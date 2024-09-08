package az.company.elibrary.service.book;

import az.company.elibrary.models.entity.Author;
import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Category;
import az.company.elibrary.models.entity.Genre;
import az.company.elibrary.models.enums.BookSortBy;
import az.company.elibrary.models.request.BookRequest;
import az.company.elibrary.models.response.BookResponse;
import az.company.elibrary.models.specification.BookCriteria;
import az.company.elibrary.models.specification.BookSpecification;
import az.company.elibrary.repository.BookRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public PagedModel<BookResponse> filterBooks(BookCriteria bookCriteria, Pageable pageable, BookSortBy sortBy) {
        Page<BookResponse> books = bookRepository
                .findAll(BookSpecification.filter(bookCriteria, sortBy), pageable)
                .map(BOOK_MAPPER::mapToBookResponse);
        return new PagedModel<>(books);
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
    public PagedModel<BookResponse> getAllBooks(Pageable pageable) {
        Page<BookResponse> books = bookRepository.findAll(pageable)
                .map(BOOK_MAPPER::mapToBookResponse);
        return new PagedModel<>(books);
    }

    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        Book book = buildBookEntity(bookRequest);
        bookRepository.save(book);
        return BOOK_MAPPER.mapToBookResponse(book);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book oldBook = getterService.getBook(id);
        Book newBook = buildBookEntity(bookRequest);
        newBook.setId(oldBook.getId());
        bookRepository.save(newBook);
        return BOOK_MAPPER.mapToBookResponse(newBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private Book buildBookEntity(BookRequest bookRequest) {
        Book book = BOOK_MAPPER.mapToBookEntity(bookRequest);

        Author author = getterService.getAuthor(bookRequest.getAuthorId());
        book.setAuthor(author);

        Category category = getterService.getCategory(bookRequest.getCategoryId());
        book.setCategory(category);

        if (!bookRequest.getGenreIds().isEmpty()) {
            List<Genre> genres = new ArrayList<>(bookRequest.getGenreIds().size());
            for (Long genreId : bookRequest.getGenreIds()) {
                genres.add(getterService.getGenre(genreId));
            }
            book.setGenres(genres);
        }

        return book;
    }

}
