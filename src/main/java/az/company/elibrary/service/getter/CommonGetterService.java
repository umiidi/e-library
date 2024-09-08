package az.company.elibrary.service.getter;

import az.company.elibrary.exception.type.NotFoundException;
import az.company.elibrary.models.entity.*;
import az.company.elibrary.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonGetterService {

    private static final String NOT_FOUND_ERROR_MESSAGE = "%s not found with id: %d";

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowingRepository borrowingRepository;
    private final ReservationRepository reservationRepository;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    private final GenreRepository genreRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
    }

    public User getUser(Long userId) {
        return findById(userRepository, userId, "User");
    }

    public Author getAuthor(Long id) {
        return findById(authorRepository, id, "Author");
    }

    public Book getBook(Long id) {
        return findById(bookRepository, id, "Book");
    }

    public Borrowing getBorrowing(Long id) {
        return findById(borrowingRepository, id, "Borrowing");
    }

    public Reservation getReservation(Long id) {
        return findById(reservationRepository, id, "Reservation");
    }

    public Rating getRating(Long id) {
        return findById(ratingRepository, id, "Rating");
    }

    public Review getReview(Long id) {
        return findById(reviewRepository, id, "Review");
    }

    public Category getCategory(Long id) {
        return findById(categoryRepository, id, "Category");
    }

    public Genre getGenre(Long id) {
        return findById(genreRepository, id, "Genre");
    }

    private <T> T findById(JpaRepository<T, Long> repository, Long id, String entityName) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE, entityName, id)));
    }

}