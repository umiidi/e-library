package az.company.elibrary.service.book;

import az.company.elibrary.models.request.BookRequest;
import az.company.elibrary.models.response.BookResponse;
import az.company.elibrary.models.specification.BookCriteria;

import java.util.List;

public interface BookService {

    List<BookResponse> searchBooks(String keyword);

    List<BookResponse> filterBooks(BookCriteria bookCriteria);

    int getBookInventory(Long bookId);

    BookResponse getBookById(Long id);

    List<BookResponse> getAllBooks();

    BookResponse addBook(BookRequest bookRequest);

    BookResponse updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

}
