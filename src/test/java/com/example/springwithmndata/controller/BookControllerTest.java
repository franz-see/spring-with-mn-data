package com.example.springwithmndata.controller;

import com.example.springwithmndata.entity.Book;
import com.example.springwithmndata.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.springwithmndata.controller.Paths.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private BookRepository bookRepository;
    
    private String basePath;

    @BeforeEach
    void setup() {
        basePath = "http://localhost:" + port;
    }

    @Test
    void getBook() {
        Book inputBook = new Book();
        inputBook.setTitle("Dummy Book");

        Book savedBook = bookRepository.save(inputBook);
        
        assertNotNull(savedBook);
        assertEquals(savedBook.getId(), savedBook.getId());
        assertEquals("Dummy Book", savedBook.getTitle());

        ResponseEntity<Book> response = restTemplate.getForEntity(
                basePath + BOOK_GET_ONE,
                Book.class,
                Map.of(
                        "id", savedBook.getId()
                ));
        Book retrievedBook = response.getBody();
        assertNotNull(retrievedBook);
        assertEquals(savedBook.getId(), retrievedBook.getId());
        assertEquals("Dummy Book", retrievedBook.getTitle());
    }

    @Test
    void saveAndGetBook() {
        Book inputBook = new Book();
        inputBook.setTitle("Dummy Book");

        ResponseEntity<Book> savedBookResponse = restTemplate.postForEntity(
                basePath + BOOK_SAVE,
                inputBook,
                Book.class);

        Book savedBook = savedBookResponse.getBody();
        assertNotNull(savedBook);
        assertEquals(savedBook.getId(), savedBook.getId());
        assertEquals("Dummy Book", savedBook.getTitle());

        ResponseEntity<Book> response = restTemplate.getForEntity(
                basePath + BOOK_GET_ONE,
                Book.class,
                Map.of(
                        "id", savedBook.getId()
                ));
        Book retrievedBook = response.getBody();
        assertNotNull(retrievedBook);
        assertEquals(savedBook.getId(), retrievedBook.getId());
        assertEquals("Dummy Book", retrievedBook.getTitle());
    }

    @Test
    void bulkSave() {
        Book inputBook1 = new Book();
        inputBook1.setTitle("Book 1");
        Book inputBook2 = new Book();
        inputBook2.setTitle(null);
        Book inputBook3 = new Book();
        inputBook3.setTitle("Book 3");
        
        List<Book> inputBooks = List.of(inputBook1, inputBook2, inputBook3);

        try {
            restTemplate.postForEntity(
                    basePath + BOOK_BULK_SAVE,
                    inputBooks,
                    Book[].class);
            fail("[GUARD] Bulk Save should have failed because one of the books did not have a title.");
        } catch (RestClientException ignore) {
            
        }

        inputBooks
                .stream()
                .map(Book::getTitle)
                .filter(Objects::nonNull)
                .forEach(title -> {
                    String url = basePath + BOOK_FIND + "?title=" + URLEncoder.encode(title, StandardCharsets.UTF_8);
                    System.out.println("Querying " + url);
                    Book[] books = restTemplate.getForEntity(
                            url,
                            Book[].class).getBody();
                    assertNotNull(books, "Searching by " + title + " should NOT have returned null");
                    assertEquals(0, books.length, title + " should NOT have been saved");
                });
    }

}