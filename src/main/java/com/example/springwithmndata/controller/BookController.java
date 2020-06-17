package com.example.springwithmndata.controller;

import com.example.springwithmndata.entity.Book;
import com.example.springwithmndata.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static com.example.springwithmndata.controller.Paths.*;

@RestController
public class BookController {
    
    private final BookService bookService;
    
    @Inject
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(BOOK_GET_ONE)
    public Book getOne(@PathVariable Long id) {
        return bookService.getOne(id);
    }

    @PostMapping(BOOK_SAVE)
    public Book save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PostMapping(BOOK_BULK_SAVE)
    public List<Book> bulkSave(@RequestBody List<Book> books) {
        return bookService.bulkSave(books);
    }
    
    @GetMapping(BOOK_FIND)
    public List<Book> find(@RequestParam String title) {
        return bookService.findAllByTitle(title);
    }
}
