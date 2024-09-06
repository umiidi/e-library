package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @EntityGraph(attributePaths = {"book"})
    List<Reservation> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"book"})
    List<Reservation> findByBookId(Long bookId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.endDate < CURRENT_DATE AND r.status = 'APPROVED'")
    @EntityGraph(attributePaths = {"book"})
    List<Reservation> findExpiredReservations();

}