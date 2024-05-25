package com.adamsoft.dataread;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "cqrs-topic", groupId = "adamsoft")
    public void consume(String message) throws IOException {
        System.out.println(message);
        JSONObject messageObj = new JSONObject(message);

        MongoClient mongoClient = MongoClients.create("mongodb://adam:wnddkd@3.35.53.113:27017");
        MongoDatabase database = mongoClient.getDatabase("itstudy");
        MongoCollection<Document> mongo_books = database.getCollection("books");
        mongo_books = database.getCollection("books");
        Document mongoBook = new Document();
        mongoBook.append("bid", messageObj.getLong("bid"));
        mongoBook.append("title", messageObj.getString("title"));
        mongoBook.append("author", messageObj.getString("author"));
        mongoBook.append("category", messageObj.getString("category"));
        mongoBook.append("pages", messageObj.getInt("pages"));
        mongoBook.append("price", messageObj.getInt("price"));
        mongoBook.append("published_date", messageObj.getString("published_date"));
        mongoBook.append("description", messageObj.getString("description"));
        mongo_books.insertOne(mongoBook);

        mongoClient.close();
    }
}
