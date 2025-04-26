package com.clavery.library.services;

import com.clavery.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    boolean isBookExists(Book book);

    Book save(Book book);

    Optional<Book> findById(String isbn);

    List<Book> listBooks();

    void deleteBookById(String isbn);
}
