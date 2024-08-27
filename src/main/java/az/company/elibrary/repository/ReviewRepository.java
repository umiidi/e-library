package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"book"})
    List<Review> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"book"})
    List<Review> findByBookId(Long bookId);

    @EntityGraph(attributePaths = {"book"})
    Optional<Review> findByUserIdAndBookId(Long userId, Long bookId);

}
