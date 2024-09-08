package az.company.elibrary.controller;

import az.company.elibrary.models.enums.BookSortBy;
import az.company.elibrary.models.request.BookRequest;
import az.company.elibrary.models.response.BookResponse;
import az.company.elibrary.models.specification.BookCriteria;
import az.company.elibrary.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static az.company.elibrary.models.constants.BookConstants.DEFAULT_SORT_FIELD;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchBooks(keyword));
    }

    @GetMapping("/filter")
    public ResponseEntity<PagedModel<BookResponse>> filterBooks(@RequestParam int page,
                                                                @RequestParam int size,
                                                                @RequestParam(defaultValue = DEFAULT_SORT_FIELD) BookSortBy sortBy,
                                                                @ModelAttribute BookCriteria bookCriteria) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookService.filterBooks(bookCriteria, pageable, sortBy));
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<Integer> getBookInventory(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookInventory(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    public ResponseEntity<PagedModel<BookResponse>> getAllBooks(@RequestParam int page,
                                                                @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.addBook(bookRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
                                                   @Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}