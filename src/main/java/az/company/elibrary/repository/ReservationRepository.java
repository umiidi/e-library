package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
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

    @Query("SELECT CASE " +
            "WHEN COUNT(r) = 0 THEN false " +
            "WHEN COUNT(r) < r.book.inventory THEN true " +
            "WHEN COUNT(r) FILTER(WHERE NOT((:startDate > r.endDate) OR (:endDate < r.startDate))) < r.book.inventory THEN true " +
            "ELSE false END " +
            "FROM Reservation r " +
            "WHERE r.book.id = :bookId " +
            "AND (r.status = 'APPROVED' OR r.status = 'COMPLETED')")
    boolean isSuitableForReservation(Long bookId, LocalDate startDate, LocalDate endDate);

}