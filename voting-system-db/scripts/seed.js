// scripts/seed.js
// Run:
//   PowerShell: $env:MONGO_URI="mongodb://localhost:27017"; mongosh --file scripts/seed.js
//   Default MONGO_URI is mongodb://localhost:27017

const crypto = require("crypto");

const DB_NAME = "voting_system";
const MONGO_URI =
  (typeof process !== "undefined" && process.env && process.env.MONGO_URI)
    ? process.env.MONGO_URI
    : "mongodb://localhost:27017";

const conn = new Mongo(MONGO_URI);
const db = conn.getDB(DB_NAME);

// ---- Customize here ----
const ELECTION_NAME = "Demo Election";
const CANDIDATE_NAMES = ["Alice", "Bob"]; // swap names if you want
const TOKEN_COUNT = 20;                   // total tokens created
const AUTO_VOTE_COUNT = 8;                // votes auto-cast using first N tokens
// -----------------------

function generateRawToken() {
  // user-facing token (what you type)
  return "v_" + crypto.randomBytes(16).toString("hex"); // 32 hex chars + prefix
}

function sha256Hex(input) {
  return crypto.createHash("sha256").update(input, "utf8").digest("hex"); // 64 hex chars
}

print(`\n== seed.js ==`);
print(`MONGO_URI: ${MONGO_URI}`);
print(`DB_NAME:   ${DB_NAME}\n`);

if (AUTO_VOTE_COUNT > TOKEN_COUNT) {
  throw new Error("AUTO_VOTE_COUNT cannot be greater than TOKEN_COUNT");
}

// Prevent duplicate seed runs (MUST be before inserting)
if (db.elections.countDocuments({ name: ELECTION_NAME }) > 0) {
  throw new Error(`Election already seeded: "${ELECTION_NAME}"`);
}

// Optional: ensure candidate name uniqueness per election (idempotent)
db.candidates.createIndex(
  { electionId: 1, name: 1 },
  { unique: true }
);

// Create election
const electionId = db.elections.insertOne({
  name: ELECTION_NAME,
  createdAt: new Date()
}).insertedId;

print(`Election created: "${ELECTION_NAME}"`);
print(`electionId: ${electionId}\n`);

// Create candidates
const candidateInsert = db.candidates.insertMany(
  CANDIDATE_NAMES.map((name) => ({ electionId, name }))
);

const candidateIds = Object.values(candidateInsert.insertedIds);

print("Candidates:");
candidateIds.forEach((id, i) => print(`- ${CANDIDATE_NAMES[i]}: ${id}`));
print("");

// Create tokens (store only hash, print raw once)
const tokenDocs = [];
const tokenPairs = []; // { raw, hash }

for (let i = 0; i < TOKEN_COUNT; i++) {
  const raw = generateRawToken();
  const hash = sha256Hex(raw);
  tokenPairs.push({ raw, hash });

  tokenDocs.push({
    electionId,
    tokenHash: hash, // store hash only
    status: "ACTIVE",
    issuedAt: new Date(),
    usedAt: null
  });
}

db.tokens.insertMany(tokenDocs);
print(`Inserted ${TOKEN_COUNT} tokens.\n`);

// Auto-cast some votes for a nicer results demo
// Use the first AUTO_VOTE_COUNT tokens so we know exactly which ones were used.
const used = tokenPairs.slice(0, AUTO_VOTE_COUNT);
const now = new Date();

let aliceVotes = 0;
let bobVotes = 0;

for (let i = 0; i < used.length; i++) {
  // Weighted-ish distribution: favor candidate 0 slightly for nicer demo results
  const pick = (i % 3 === 0) ? 1 : 0; // ~2/3 for candidate 0, ~1/3 for candidate 1
  const candidateId = candidateIds[pick];

  db.votes.insertOne({
    electionId,
    tokenHash: used[i].hash,
    candidateId,
    createdAt: new Date(now.getTime() + i * 1000)
  });

  if (pick === 0) aliceVotes++;
  else bobVotes++;

  db.tokens.updateOne(
    { electionId, tokenHash: used[i].hash },
    { $set: { status: "USED", usedAt: new Date(now.getTime() + i * 1000) } }
  );
}

print(`Auto-cast ${AUTO_VOTE_COUNT} votes.`);
print(
  `Vote split (approx): ${CANDIDATE_NAMES[0]}=${aliceVotes}, ${CANDIDATE_NAMES[1]}=${bobVotes}\n`
);

// Print remaining ACTIVE raw tokens for manual testing
const remaining = tokenPairs.slice(AUTO_VOTE_COUNT);

print("IMPORTANT: Remaining ACTIVE raw tokens (store these; raw not saved in DB):");
remaining.forEach((t, i) => print(`${i + 1}. ${t.raw}`));

print("\nJava note:");
print("- When user enters raw token, compute SHA-256 hex, use that as tokenHash.");
print("- votes.tokenHash and tokens.tokenHash store the SHA-256 hash.\n");

print("== Done seed.js ==\n");