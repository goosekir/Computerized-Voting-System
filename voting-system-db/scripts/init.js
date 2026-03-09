// scripts/init.js
// Run:
//   PowerShell: $env:MONGO_URI="mongodb://localhost:27017"; mongosh --file scripts/init.js
//   Default MONGO_URI is mongodb://localhost:27017

const DB_NAME = "voting_system";
const MONGO_URI = (typeof process !== "undefined" && process.env && process.env.MONGO_URI)
  ? process.env.MONGO_URI
  : "mongodb://localhost:27017";

print(`\n== init.js ==`);
print(`MONGO_URI: ${MONGO_URI}`);
print(`DB_NAME:   ${DB_NAME}\n`);

const conn = new Mongo(MONGO_URI);
const db = conn.getDB(DB_NAME);

// ---------- Validators ----------
const votesValidator = {
  $jsonSchema: {
    bsonType: "object",
    required: ["electionId", "tokenHash", "candidateId", "createdAt"],
    properties: {
      electionId: { bsonType: "objectId" },
      tokenHash: { bsonType: "string", minLength: 64, maxLength: 64 }, // SHA-256 hex
      candidateId: { bsonType: "objectId" },
      createdAt: { bsonType: "date" }
    },
    additionalProperties: true
  }
};

const tokensValidator = {
  $jsonSchema: {
    bsonType: "object",
    required: ["electionId", "tokenHash", "status", "issuedAt"],
    properties: {
      electionId: { bsonType: "objectId" },
      tokenHash: { bsonType: "string", minLength: 64, maxLength: 64 }, // SHA-256 hex
      status: { bsonType: "string", enum: ["ACTIVE", "USED", "REVOKED"] },
      issuedAt: { bsonType: "date" },
      usedAt: { bsonType: ["date", "null"] }
    },
    additionalProperties: true
  }
};

// ---------- Create collections if missing (and apply validators) ----------
function ensureCollection(name, validator = null) {
  const exists = db.getCollectionNames().includes(name);
  if (!exists) {
    const options = validator
      ? { validator, validationLevel: "strict", validationAction: "error" }
      : {};
    db.createCollection(name, options);
    print(`Created collection: ${name}`);
  } else {
    if (validator) {
      db.runCommand({
        collMod: name,
        validator,
        validationLevel: "strict",
        validationAction: "error"
      });
      print(`Updated validation: ${name}`);
    } else {
      print(`Collection exists: ${name}`);
    }
  }
}

ensureCollection("elections");
ensureCollection("candidates");
ensureCollection("tokens", tokensValidator);
ensureCollection("votes", votesValidator);

// ---------- Indexes ----------
print("\n== Creating indexes ==\n");

// votes: prevent double voting
db.votes.createIndex(
  { electionId: 1, tokenHash: 1 },
  { unique: true }
);

// votes: speed results queries
db.votes.createIndex(
  { electionId: 1, candidateId: 1 },
  { name: "idx_votes_by_candidate" }
);

// tokens: prevent duplicate tokens
db.tokens.createIndex(
  { electionId: 1, tokenHash: 1 },
  { unique: true }
);

// candidates: list candidates by election quickly
db.candidates.createIndex(
  { electionId: 1 },
  { name: "idx_candidates_by_election" }
);

print("\n== Done init.js ==\n");