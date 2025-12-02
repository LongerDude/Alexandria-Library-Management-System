package com.LongerDude.LMS.controller;

import com.LongerDude.LMS.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.LongerDude.LMS.repository.BookRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/books") //mapping to the books table
public class BookController {
    private final BookRepository bookRepository;
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    private ResponseEntity<Void> createBook (@RequestBody Book book, UriComponentsBuilder ucb) {
        Book savedBook = bookRepository.save(book);
        URI locationOfNewBook = ucb.path("/api/books/{id}")
                                   .buildAndExpand(savedBook.getId())
                                   .toUri();
        return ResponseEntity.created(locationOfNewBook).build();

    }
    @GetMapping
    private ResponseEntity<Iterable<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }


    @GetMapping("/{id}")
    private ResponseEntity<Book> getBook(@PathVariable Long id) {
       Optional<Book> book = bookRepository.findById(id);
       if (book.isPresent()) {
           System.out.println(book.get());
           return ResponseEntity.ok(book.get());

       } else {
           return ResponseEntity.notFound().build();
       }
    }
    @PutMapping("/{id}")
    private ResponseEntity<Void> updateBook (@PathVariable Long id,@RequestBody Book bookUpdate) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    Book updatedBook = new Book(
                            id,
                            existingBook.getTitle(),
                            existingBook.getAuthor(),
                            bookUpdate.getAmount()
                    );

                    bookRepository.save(updatedBook);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
