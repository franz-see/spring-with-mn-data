package com.example.springwithmndata.service;

import com.example.springwithmndata.entity.Book;
import com.example.springwithmndata.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookService {
    
    private final BookRepository bookRepository;

    @Inject
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getOne(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
