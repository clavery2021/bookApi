package com.clavery.library.services.impl;

import com.clavery.library.domain.Book;
import com.clavery.library.domain.BookEntity;
import com.clavery.library.repositories.BookRepository;
import com.clavery.library.services.implementation.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.clavery.library.TestData.testBook;
import static com.clavery.library.TestData.testBookEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testBookIsSaved() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.save(book);
        assertEquals(book, result);
    }

    @Test
    public void testFindByIdReturnBookWhenExists() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = underTest.findById(book.getIsbn());
        assertEquals(Optional.of(book), result);
    }

    @Test
    public void testListBooksReturnsEmptyListWhenNoBookExist() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        final List<Book> result = underTest.listBooks();
        assertEquals(0, result.size());
    }


    @Test
    public void testListBooksReturnsBooks() {
        final BookEntity bookEntity = testBookEntity();
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.listBooks();
        assertEquals(1, result.size());
    }

    @Test
    public void testIsBookExistsReturnsFalse() {
        when(bookRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isBookExists(testBook());
        assertEquals(false, result);
    }

    @Test
    public void testIsBookExistsReturnsTrueWhenBookExists() {
        when(bookRepository.existsById(any())).thenReturn(true);
        final boolean result = underTest.isBookExists(testBook());
        assertEquals(true, result);
    }

    @Test
    public void testDeleteBookDeletesBook() {
        final String isbn = "131241";
        underTest.deleteBookById(isbn);
        verify(bookRepository, times(1)).deleteById(isbn);
    }


}
