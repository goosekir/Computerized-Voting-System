package com.example.db;

import com.mongodb.client.MongoDatabase;

public class TestMongo {

    public static void test() {
        MongoDatabase db = MongoProvider.getDatabase();
        System.out.println("Connected to DB: " + db.getName());
    }
}