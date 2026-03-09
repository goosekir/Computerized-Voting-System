package com.example;

import com.example.db.MongoProvider;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DB {

    private static final SecureRandom random = new SecureRandom();

    private static MongoDatabase getDatabase() {
        return MongoProvider.getDatabase();
    }

    private static MongoCollection<Document> electionsCollection() {
        return getDatabase().getCollection("elections");
    }

    private static MongoCollection<Document> candidatesCollection() {
        return getDatabase().getCollection("candidates");
    }

    private static MongoCollection<Document> tokensCollection() {
        return getDatabase().getCollection("tokens");
    }

    private static MongoCollection<Document> votesCollection() {
        return getDatabase().getCollection("votes");
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public static void createElection(String name, LocalDateTime startAt, LocalDateTime endAt) {
        Document election = new Document("name", name)
                .append("createdAt", new Date())
                .append("open", true);

        if (startAt != null) {
            election.append("startAt", Date.from(startAt.atZone(ZoneId.systemDefault()).toInstant()));
        }

        if (endAt != null) {
            election.append("endAt", Date.from(endAt.atZone(ZoneId.systemDefault()).toInstant()));
        }

        electionsCollection().insertOne(election);
    }

    public static void addCandidate(String electionId, String name) {
        Document election = electionsCollection().find(new Document("name", electionId)).first();

        if (election == null) {
            System.out.println("ERROR: NO ELECTION OF THAT NAME FOUND");
            return;
        }

        ObjectId electionObjectId = election.getObjectId("_id");

        Document candidate = new Document("electionId", electionObjectId)
                .append("name", name)
                .append("createdAt", new Date());

        candidatesCollection().insertOne(candidate);
    }

    public static String generateTokenForVoter(String voterName, String electionId) {
        Document election = electionsCollection().find(
                new Document("name", electionId).append("open", true)
        ).first();

        if (election == null) {
            System.out.println("ERROR: NO ACTIVE ELECTION FOUND");
            return null;
        }

        ObjectId electionObjectId = election.getObjectId("_id");

        String rawToken;
        String tokenHash;

        do {
            rawToken = String.valueOf(100000 + random.nextInt(900000));
            tokenHash = sha256(rawToken);
        } while (tokensCollection().find(
                new Document("electionId", electionObjectId).append("tokenHash", tokenHash)
        ).first() != null);

        Document tokenDoc = new Document("electionId", electionObjectId)
                .append("tokenHash", tokenHash)
                .append("status", "ACTIVE")
                .append("issuedAt", new Date())
                .append("usedAt", null)
                .append("voterName", voterName);

        tokensCollection().insertOne(tokenDoc);

        return rawToken;
    }

    public void openElection(String electionId) {
        electionsCollection().updateMany(
                new Document(),
                new Document("$set", new Document("open", false))
        );

        electionsCollection().updateOne(
                new Document("name", electionId),
                new Document("$set", new Document("open", true))
        );
    }

    public void closeElection(String electionId) {
        electionsCollection().updateOne(
                new Document("name", electionId),
                new Document("$set", new Document("open", false))
        );
    }

    public static String getActiveElection() {
        Document activeElection = electionsCollection().find(new Document("open", true)).first();

        if (activeElection == null) {
            return "NULL";
        }

        return activeElection.getString("name");
    }

    public static String[] getCandidates(String electionId) {
        Document election = electionsCollection().find(new Document("name", electionId)).first();

        if (election == null) {
            System.out.println("ERROR: NO ELECTION OF THAT NAME FOUND");
            return new String[]{};
        }

        ObjectId electionObjectId = election.getObjectId("_id");

        FindIterable<Document> docs = candidatesCollection().find(
                new Document("electionId", electionObjectId)
        );

        List<String> candidateNames = new ArrayList<>();
        for (Document doc : docs) {
            candidateNames.add(doc.getString("name"));
        }

        return candidateNames.toArray(new String[0]);
    }

    public static boolean castVote(String rawToken, String electionId, String candidateId) {
        Document election = electionsCollection().find(
                new Document("name", electionId).append("open", true)
        ).first();

        if (election == null) {
            System.out.println("ERROR: NO ACTIVE ELECTION FOUND");
            return false;
        }

        ObjectId electionObjectId = election.getObjectId("_id");

        Document candidate = candidatesCollection().find(
                new Document("electionId", electionObjectId).append("name", candidateId)
        ).first();

        if (candidate == null) {
            System.out.println("ERROR: CANDIDATE NOT FOUND");
            return false;
        }

        String tokenHash = sha256(rawToken);

        Document tokenDoc = tokensCollection().find(
                new Document("electionId", electionObjectId)
                        .append("tokenHash", tokenHash)
                        .append("status", "ACTIVE")
        ).first();

        if (tokenDoc == null) {
            System.out.println("ERROR: INVALID OR USED TOKEN");
            return false;
        }

        Document existingVote = votesCollection().find(
                new Document("electionId", electionObjectId).append("tokenHash", tokenHash)
        ).first();

        if (existingVote != null) {
            System.out.println("ERROR: TOKEN ALREADY USED TO VOTE");
            return false;
        }

        Document vote = new Document("electionId", electionObjectId)
                .append("tokenHash", tokenHash)
                .append("candidateId", candidate.getObjectId("_id"))
                .append("createdAt", new Date());

        votesCollection().insertOne(vote);

        tokensCollection().updateOne(
                new Document("_id", tokenDoc.getObjectId("_id")),
                new Document("$set", new Document("status", "USED")
                        .append("usedAt", new Date()))
        );

        return true;
    }

    public static void getResults(String electionId) {
        Document election = electionsCollection().find(new Document("name", electionId)).first();

        if (election == null) {
            System.out.println("ERROR: NO ELECTION FOUND");
            return;
        }

        ObjectId electionObjectId = election.getObjectId("_id");

        List<Document> candidateDocs = candidatesCollection()
                .find(new Document("electionId", electionObjectId))
                .into(new ArrayList<>());

        System.out.println("-----RESULTS-----");

        for (Document candidate : candidateDocs) {
            long count = votesCollection().countDocuments(
                    new Document("electionId", electionObjectId)
                            .append("candidateId", candidate.getObjectId("_id"))
            );

            System.out.println(candidate.getString("name") + " scored " + count + " votes");
        }

        System.out.println("------END------");
    }

    public static int[] getResultsArray(String electionId) {
        Document election = electionsCollection().find(new Document("name", electionId)).first();

        if (election == null) {
            return new int[]{};
        }

        ObjectId electionObjectId = election.getObjectId("_id");

        List<Document> candidateDocs = candidatesCollection()
                .find(new Document("electionId", electionObjectId))
                .into(new ArrayList<>());

        int[] results = new int[candidateDocs.size()];

        for (int i = 0; i < candidateDocs.size(); i++) {
            Document candidate = candidateDocs.get(i);

            long count = votesCollection().countDocuments(
                    new Document("electionId", electionObjectId)
                            .append("candidateId", candidate.getObjectId("_id"))
            );

            results[i] = (int) count;
        }

        return results;
    }

    public static void getAuditEvents(String electionId) {
        // Add later if needed
    }
}