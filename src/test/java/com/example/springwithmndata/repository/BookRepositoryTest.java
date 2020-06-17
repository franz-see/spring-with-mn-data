package com.example.springwithmndata.repository;

import com.example.springwithmndata.entity.Book;
import com.example.springwithmndata.repository.BookRepository;
import io.micronaut.context.BeanContext;
import io.micronaut.data.annotation.Query;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class BookRepositoryTest {

    @Inject
    BookRepository bookRepository;

    @Inject
    BeanContext beanContext;

    @Test
    void testQuery() {
        String query = beanContext.getBeanDefinition(BookRepository.class)
                .getRequiredMethod("findByTitle", String.class)
                .getAnnotationMetadata().stringValue(Query.class).get();

        System.out.println("query = " + query);
    }


    @Test
    void testRetrieveBook() {
        Book dino = bookRepository.findByTitle("Dino").orElse(null);
        assertNull(dino);
    }
    
}