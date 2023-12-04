package com.salvaroca.obrestdatajpa.controller;

import com.salvaroca.obrestdatajpa.entity.Book;
import com.salvaroca.obrestdatajpa.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Book Controller")
public class BookController {
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * GET method to obtain all books in repository
     * @return 200: JSON with all books
     */
    @Operation(
            description = "GET method to obtain all books in repository",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns JSON with all books"
                    )
            }
    )

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * GET method to obtain a specific book by its id
     * @param id ID of the book to be retrieved
     * @return 200: JSON with specific book - 404: book not found
     */

    @Operation(
            description = "GET method to obtain a specific book by its id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the book to be retrieved"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns JSON with specific book"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found"
                    )
            }
    )

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            return ResponseEntity.ok(bookOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST method to register a new book
     * @param book Book object to be registered
     * @return 200: registered book object - 400: bad request (book already exists)
     */
    @Operation(
            description = "POST method to register a new book",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns registered book object"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request (book already exists)"
                    )
            }
    )

    @PostMapping("/books")
    public ResponseEntity<Book> registerBook(@RequestBody Book book) {
        if (book.getId() != null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(bookRepository.save(book));
        }
    }

    /**
     * PUT method to edit the details of an existing book
     * @param book Book object to be edited
     * @return 200: edited book object - 400: bad request - 404: book not found
     */
    @Operation(
            description = "PUT method to edit the details of an existing book",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns edited book object"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found"
                    )
            }
    )

    @PutMapping("/books")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        if (book.getId() != null) {
            Optional<Book> bookOpt = bookRepository.findById(book.getId());
            if (bookOpt.isPresent()) {
                return ResponseEntity.ok(bookRepository.save(book));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE method to erase all books from repository
     * @return 200: repository emptied, shows no content
     */
    @Operation(
            description = "DELETE method to erase all books from repository",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Repository emptied, shows no content"
                    )
            }
    )

    @DeleteMapping("/books")
    public ResponseEntity<Book> deleteAllBooks() {
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE method to remove a specific book by its id
     * @param id ID of the book to be deleted
     * @return 200: book deleted, shows no content - 404: book not found
     */
    @Operation(
            description = "DELETE method to remove a specific book by its id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the book to be deleted"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book deleted, shows no content"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found"
                    )
            }
    )

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {

        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
