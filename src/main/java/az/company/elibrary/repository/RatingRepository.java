package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Rating;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @EntityGraph(attributePaths = {"book"})
    List<Rating> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"book"})
    List<Rating> findByBookId(Long bookId);

    @EntityGraph(attributePaths = {"book"})
    Optional<Rating> findByUserIdAndBookId(Long userId, Long bookId);

}
