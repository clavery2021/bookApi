package com.clavery.library.services.implementation;

import com.clavery.library.domain.Book;
import com.clavery.library.domain.BookEntity;
import com.clavery.library.repositories.BookRepository;
import com.clavery.library.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        return bookEntityToBook(bookRepository.save(bookEntity));
    }

    @Override
    public Optional<Book> findById(String isbn) {
        final Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook.map(book -> bookEntityToBook(book));
    }

    @Override
    public List<Book> listBooks() {
        final List<BookEntity> foundBooks = bookRepository.findAll();
        return foundBooks.stream().map(book -> bookEntityToBook(book)).collect(Collectors.toList());
    }

    @Override
    public boolean isBookExists(Book book) {
        return bookRepository.existsById(book.getIsbn());
    }

    @Override
    public void deleteBookById(String isbn) {
        try {
            bookRepository.deleteById(isbn);
        } catch(final EmptyResultDataAccessException ex) {
            log.debug("Attempted to delete non existing book", ex);
        }
    }

    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
                         .isbn(book.getIsbn())
                         .title(book.getTitle())
                         .author(book.getAuthor())
                         .build();
    }

    private Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
                   .isbn(bookEntity.getIsbn())
                   .title(bookEntity.getTitle())
                   .author(bookEntity.getAuthor())
                   .build();
    }
}
