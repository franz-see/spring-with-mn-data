package com.example.springwithmndata.repository;

import com.example.springwithmndata.entity.Book;
import io.micronaut.context.BeanContext;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class BookRepositoryTest {

    @Inject
    BookRepository bookRepository;

    @Inject
    BeanContext beanContext;

    @Inject
    SynchronousTransactionManager<Connection> transactionManager;

    @Test
    void readOnly() {
        try {
            transactionManager.executeRead(status -> {
                status.getConnection().setReadOnly(true);
                Book book = new Book();
                book.setTitle("New Book");
                return bookRepository.save(book);
            });
            fail("Should NOT have been able to save in a read only transaction");
        } catch (DataAccessException ignore) {
            
        }
    }
    
    @Test
    void testQuery() {
        String query = beanContext.getBeanDefinition(BookRepository.class)
                .getRequiredMethod("findAllByTitle", String.class)
                .getAnnotationMetadata().stringValue(Query.class).orElse(null);

        System.out.println("query = " + query);
    }


    @Test
    void testFindAllByTitle() {
        List<Book> books = bookRepository.findAllByTitle("Dino");
        assertNotNull(books);
        assertEquals(0, books.size());
    }

    @Test
    void testSaveThenFind() {
        String dummyTitle = "My Hilarious Book of Puns!";
        
        Book inputBook = new Book();
        inputBook.setTitle(dummyTitle);
        bookRepository.save(inputBook);
        
        List<Book> books = bookRepository.findAllByTitle(dummyTitle);
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(dummyTitle, books.get(0).getTitle());
    }

    @Test
    void saveWithNullTitle() {
        Book book = new Book();
        book.setPages(10);
        
        try {
            bookRepository.save(book);
            fail("Should have thrown ConstraintViolationException");
        } catch (ConstraintViolationException ignore) {
            
        }
    }
}
