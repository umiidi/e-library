package az.company.elibrary.service.author;

import az.company.elibrary.models.request.AuthorRequest;
import az.company.elibrary.models.response.AuthorResponse;
import az.company.elibrary.models.response.BookResponse;

import java.util.List;

public interface AuthorService {

    AuthorResponse getAuthorById(Long id);

    List<AuthorResponse> getAllAuthors();

    List<AuthorResponse> searchAuthorsByName(String keyword);

    AuthorResponse addAuthor(AuthorRequest authorRequest);

    AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest);

    void deleteAuthor(Long id);

    void removeBookFromAuthor(Long authorId, Long bookId);

    List<BookResponse> getBooksByAuthor(Long authorId);

}
