package com.adamsoft.dataread;

import com.mongodb.client.*;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequiredArgsConstructor
public class BookController {
    @GetMapping("/cqrs/book")
    public ResponseEntity<List> getBooks(){
        MongoClient mongoClient = MongoClients.create("mongodb://adam:wnddkd@43.200.180.22:27017");
        MongoDatabase database = mongoClient.getDatabase("itstudy");
        MongoCollection<Document> mongo_books = database.getCollection("books");
        // 데이터를 저장해서 리턴할 인스턴스 생성
        List<Document> list = new ArrayList<Document>();
        try {

            try (MongoCursor<Document> cur = mongo_books.find().iterator()) 			{
                while (cur.hasNext()) {
                    Document doc = cur.next();
                    list.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}


