package com.vkeonline.bookstore.catalogservice.service;

import com.vkeonline.bookstore.catalogservice.domain.Book;
import com.vkeonline.bookstore.catalogservice.exception.BookAlreadyExistsException;
import com.vkeonline.bookstore.catalogservice.exception.BookNotFoundException;
import com.vkeonline.bookstore.catalogservice.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        if (!bookRepository.existsByIsbn(isbn)) {
            throw new BookNotFoundException(isbn);
        }
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book) {
        Optional<Book> existingBook = bookRepository.findByIsbn(isbn);
        if (existingBook.isEmpty()) {
            return addBookToCatalog(book);
        }
        var bookToUpdate = new Book(
                existingBook.get().getId(),
                existingBook.get().getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getPublisher(),
                existingBook.get().getCreatedDate(),
                existingBook.get().getLastModifiedDate(),
                existingBook.get().getVersion());
        return bookRepository.save(bookToUpdate);
    }

}
