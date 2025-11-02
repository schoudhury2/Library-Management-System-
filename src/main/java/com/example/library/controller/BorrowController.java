
package com.example.library.controller;

import com.example.library.model.BorrowRecord;
import com.example.library.model.Book;
import com.example.library.model.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowRecordRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public BorrowController(BookRepository bookRepository, MemberRepository memberRepository, BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    @PostMapping("/borrow/{bookId}/{memberId}")
    public ResponseEntity<?> borrow(@PathVariable Long bookId, @PathVariable Long memberId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        if (book.getQuantity() <= 0) return ResponseEntity.badRequest().body("No copies available");
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        BorrowRecord br = new BorrowRecord();
        br.setBook(book);
        br.setMember(member);
        br.setBorrowDate(LocalDate.now());
        borrowRecordRepository.save(br);
        return ResponseEntity.ok("Borrowed");
    }

    @PostMapping("/return/{recordId}")
    public ResponseEntity<?> returnBook(@PathVariable Long recordId) {
        BorrowRecord br = borrowRecordRepository.findById(recordId).orElseThrow(() -> new RuntimeException("Record not found"));
        if (br.getReturnDate() != null) return ResponseEntity.badRequest().body("Already returned");
        Book book = br.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
        br.setReturnDate(LocalDate.now());
        borrowRecordRepository.save(br);
        return ResponseEntity.ok("Returned");
    }
}
