package az.company.elibrary.service.book;

import az.company.elibrary.models.enums.BookSortBy;
import az.company.elibrary.models.request.BookRequest;
import az.company.elibrary.models.response.BookResponse;
import az.company.elibrary.models.specification.BookCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface BookService {

    List<BookResponse> searchBooks(String keyword);

    PagedModel<BookResponse> filterBooks(BookCriteria bookCriteria, Pageable pageable, BookSortBy sortBy);

    int getBookInventory(Long bookId);

    BookResponse getBookById(Long id);

    PagedModel<BookResponse> getAllBooks(Pageable pageable);

    BookResponse addBook(BookRequest bookRequest);

    BookResponse updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

}
