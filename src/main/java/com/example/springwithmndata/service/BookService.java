package com.example.springwithmndata.service;

import com.example.springwithmndata.entity.Book;
import com.example.springwithmndata.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Book> findAllByTitle(String title) {
        return bookRepository.findAllByTitle(title);
    }

    public List<Book> bulkSave(List<Book> books) {
        // we are saving one by one here to be able to test transactions. If transaction is properly setup, if one of
        // the books fail, then all books should fail.
        return books.stream()
                .map(bookRepository::save)
                .collect(Collectors.toList());
    }
}
