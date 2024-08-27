package az.company.elibrary.service.author;

import az.company.elibrary.models.entity.Author;
import az.company.elibrary.models.request.AuthorRequest;
import az.company.elibrary.models.response.AuthorResponse;
import az.company.elibrary.models.response.BookResponse;
import az.company.elibrary.repository.AuthorRepository;
import az.company.elibrary.repository.BookRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static az.company.elibrary.mapper.AuthorMapper.AUTHOR_MAPPER;
import static az.company.elibrary.mapper.BookMapper.BOOK_MAPPER;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CommonGetterService getterService;

    @Override
    public AuthorResponse getAuthorById(Long id) {
        Author author = getterService.getAuthor(id);
        return AUTHOR_MAPPER.mapToAuthorResponse(author);
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(AUTHOR_MAPPER::mapToAuthorResponse)
                .toList();
    }

    @Override
    public List<AuthorResponse> searchAuthorsByName(String keyword) {
        return authorRepository.findAuthorsByKeyword(keyword).stream()
                .map(AUTHOR_MAPPER::mapToAuthorResponse)
                .toList();
    }

    @Override
    public AuthorResponse addAuthor(AuthorRequest authorRequest) {
        Author author = AUTHOR_MAPPER.mapToAuthorEntity(authorRequest);
        authorRepository.save(author);
        return AUTHOR_MAPPER.mapToAuthorResponse(author);
    }

    @Override
    public AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest) {
        Author author = getterService.getAuthor(id);
        AUTHOR_MAPPER.updateEntity(authorRequest, author);
        authorRepository.save(author);
        return AUTHOR_MAPPER.mapToAuthorResponse(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeBookFromAuthor(Long authorId, Long bookId) {
        bookRepository.deleteBookByIdAndAuthorId(bookId, authorId);
    }

    @Override
    public List<BookResponse> getBooksByAuthor(Long authorId) {
        Author author = getterService.getAuthor(authorId);
        return author.getBooks().stream()
                .map(BOOK_MAPPER::mapToBookResponse)
                .toList();
    }

}
