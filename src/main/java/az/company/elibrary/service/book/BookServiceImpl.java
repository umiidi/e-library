package az.company.elibrary.service.book;

import az.company.elibrary.models.entity.Author;
import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.request.BookRequest;
import az.company.elibrary.models.response.BookResponse;
import az.company.elibrary.models.specification.BookCriteria;
import az.company.elibrary.models.specification.BookSpecification;
import az.company.elibrary.repository.BookRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.elibrary.mapper.BookMapper.BOOK_MAPPER;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommonGetterService getterService;

    @Override
    public List<BookResponse> searchBooks(String keyword) {
        List<Book> books = bookRepository.findAll(BookSpecification.search(keyword));
        return books.stream()
                .map(BOOK_MAPPER::mapToBookResponse)
                .toList();
    }

    @Override
    public List<BookResponse> filterBooks(BookCriteria bookCriteria) {
        List<Book> books = bookRepository.findAll(BookSpecification.filter(bookCriteria));
        return books.stream()
                .map(BOOK_MAPPER::mapToBookResponse)
                .toList();
    }

    @Override
    public int getBookInventory(Long bookId) {
        Book book = getterService.getBook(bookId);
        return book.getInventory();
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = getterService.getBook(id);
        return BOOK_MAPPER.mapToBookResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BOOK_MAPPER::mapToBookResponse)
                .toList();
    }

    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        Author author = getterService.getAuthor(bookRequest.getAuthorId());
        Book book = BOOK_MAPPER.mapToBookEntity(bookRequest, author);
        bookRepository.save(book);
        return BOOK_MAPPER.mapToBookResponse(book);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book book = getterService.getBook(id);
        BOOK_MAPPER.updateEntity(bookRequest, book);
        book.setAuthor(getterService.getAuthor(bookRequest.getAuthorId()));
        bookRepository.save(book);
        return BOOK_MAPPER.mapToBookResponse(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

}
