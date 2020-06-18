package com.example.springwithmndata.config;

import com.example.springwithmndata.repository.BookRepository;
import io.micronaut.context.ApplicationContext;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

import static java.util.Collections.emptyMap;

@Configuration
public class MicronautConfig {
    
    @Bean
    public ApplicationContext micronautApplicationContext() {
        ApplicationContext applicationContext = ApplicationContext.build(emptyMap()).build();
        applicationContext.start();
        return applicationContext;
    }
    
    @Bean
    public BookRepository bookRepository(ApplicationContext applicationContext) {
        return applicationContext.getBean(BookRepository.class);
    }
    
    @SuppressWarnings("unchecked")
    @Bean
    public SynchronousTransactionManager<Connection> transactionManager(ApplicationContext applicationContext) {
        return (SynchronousTransactionManager<Connection> ) applicationContext.getBean(SynchronousTransactionManager.class);
    }
    
}
