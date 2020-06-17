package com.example.springwithmndata.controller;

import com.example.springwithmndata.service.BookService;
import com.example.springwithmndata.entity.Book;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.example.springwithmndata.controller.Paths.BOOK_GET_ONE;
import static com.example.springwithmndata.controller.Paths.BOOK_SAVE;

@RestController
public class BookController {
    
    private final BookService bookService;
    
    @Inject
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseBody
    @GetMapping(BOOK_GET_ONE)
    public Book getOne(@PathVariable Long id) {
        return bookService.getOne(id);
    }
    
    @ResponseBody
    @PostMapping(BOOK_SAVE)
    public Book save(@RequestBody Book book) {
        return bookService.save(book);
    }
}
