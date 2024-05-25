package com.adamsoft.dataread;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
@RequiredArgsConstructor
public class StartListener implements ApplicationListener<ApplicationStartedEvent> {
    private final BookRepository bookRepository;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        //MySQL 데이터 전부 읽어오기
        List<Book> books = bookRepository.findAll();
        //MongoDB 연결
        MongoClient mongoClient = MongoClients.create("mongodb://adam:wnddkd@3.35.53.113:27017");
        //MongoDB의 데이터베이스 연결
        MongoDatabase database = mongoClient.getDatabase("itstudy");
        //컬렉션 연결
        MongoCollection<Document> mongo_books = database.getCollection("books");
        //기존 데이터 삭제
        mongo_books.drop();
        //새로운 컬렉션 생성
        mongo_books = database.getCollection("books");
        for(Book book : books) {
            Document mongoBook = new Document();
            mongoBook.append("bid", book.getBid());
            mongoBook.append("title", book.getTitle());
            mongoBook.append("author", book.getAuthor());
            mongoBook.append("category", book.getCategory());
            mongoBook.append("pages", book.getPages());
            mongoBook.append("price", book.getPrice());
            mongoBook.append("published_date", book.getPublished_date().toString());
            mongoBook.append("description", book.getDescription());
            mongo_books.insertOne(mongoBook);
        }
        mongoClient.close();
    }
}
