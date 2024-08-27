package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findAllByUserId(Long userId);

}
