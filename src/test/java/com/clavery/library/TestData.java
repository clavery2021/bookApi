package com.clavery.library;

import com.clavery.library.domain.Book;
import com.clavery.library.domain.BookEntity;

public final class TestData {

    private TestData() {

    }

    public static Book testBook() {
       return Book.builder()
                  .isbn("12415423")
                  .author("Virgina Woolf")
                  .title("The Waves")
                  .build();
    }

    public static BookEntity testBookEntity() {
        return BookEntity.builder()
                        .isbn("12415423")
                        .author("Virgina Woolf")
                        .title("The Waves")
                        .build();
    }

}
