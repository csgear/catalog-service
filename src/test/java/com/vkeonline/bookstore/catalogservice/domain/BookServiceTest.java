package com.vkeonline.bookstore.catalogservice.domain;

import com.vkeonline.bookstore.catalogservice.exception.BookAlreadyExistsException;
import com.vkeonline.bookstore.catalogservice.exception.BookNotFoundException;
import com.vkeonline.bookstore.catalogservice.repository.BookRepository;
import com.vkeonline.bookstore.catalogservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenBookToCreateAlreadyExistsThenThrows() {
        var bookIsbn = "1234561232";
        var bookToCreate = Book.build(bookIsbn, "Title", "Author", 9.90, "Polarsophia");
        when(bookRepository.existsByIsbn(bookIsbn)).thenReturn(true);
        assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage("A book with ISBN " + bookIsbn + " already exists.");
    }

    @Test
    void whenBookToDeleteDoesNotExistThenThrows() {
        var bookIsbn = "1234561232";
        when(bookRepository.existsByIsbn(bookIsbn)).thenReturn(false);
        assertThatThrownBy(() -> bookService.removeBookFromCatalog(bookIsbn))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("The book with ISBN " + bookIsbn + " was not found.");
    }

}
