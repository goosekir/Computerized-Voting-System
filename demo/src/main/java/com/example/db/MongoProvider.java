package com.example.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoProvider {

    private static final MongoClient client =
            MongoClients.create("mongodb://localhost:27017");

    private static final MongoDatabase database =
            client.getDatabase("voting_system");

    public static MongoDatabase getDatabase() {
        return database;
    }
}