package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Borrowing;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    @EntityGraph(attributePaths = {"user", "book"})
    List<Borrowing> findAllByUserId(Long userId);

}
