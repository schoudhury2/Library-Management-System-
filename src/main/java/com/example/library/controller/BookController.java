
package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public List<Book> getAll() { return bookService.getAllBooks(); }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Book book) { return ResponseEntity.ok(bookService.addBook(book)); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) { return ResponseEntity.ok(bookService.updateBook(id, book)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) { bookService.deleteBook(id); return ResponseEntity.ok("Deleted"); }
}
