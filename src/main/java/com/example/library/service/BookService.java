
package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    public List<Book> getAllBooks() { return bookRepository.findAll(); }

    public Book addBook(Book book) { return bookRepository.save(book); }

    public void deleteBook(Long id) { bookRepository.deleteById(id); }

    public Book updateBook(Long id, Book newBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            book.setIsbn(newBook.getIsbn());
            book.setQuantity(newBook.getQuantity());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
