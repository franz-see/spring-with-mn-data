package com.example.springwithmndata.controller;

import com.example.springwithmndata.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.example.springwithmndata.controller.Paths.BOOK_GET_ONE;
import static com.example.springwithmndata.controller.Paths.BOOK_SAVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private String basePath;


    @BeforeEach
    void setup() {
        basePath = "http://localhost:" + port;
    }
    
    @Test
    public void saveAndGetBook() {
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

}