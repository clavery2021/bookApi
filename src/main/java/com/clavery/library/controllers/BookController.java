package com.clavery.library.controllers;

import com.clavery.library.domain.Book;
import com.clavery.library.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    
    private final BookService bookService;
    
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createUpdateBook(@PathVariable final String isbn,
                                                 @RequestBody final Book book) {
        book.setIsbn(isbn);

        final boolean isBookExists = bookService.isBookExists(book);
        final Book savedBook  = bookService.save(book);

        if (isBookExists) {
            return new ResponseEntity<>(savedBook, HttpStatus.OK);
        }
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn) {
        final Optional<Book> foundBook = bookService.findById(isbn);
        return foundBook.map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<>(bookService.listBooks(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> deleteBook(@PathVariable final String isbn) {
        bookService.deleteBookById(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
