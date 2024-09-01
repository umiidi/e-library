package az.company.elibrary.controller;

import az.company.elibrary.models.request.ReservationRequest;
import az.company.elibrary.models.request.ReservationStatusUpdateRequest;
import az.company.elibrary.models.response.ReservationResponse;
import az.company.elibrary.service.reservation.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/me")
    public ResponseEntity<List<ReservationResponse>> getRatingsByAuthenticatedUser() {
        return ResponseEntity.ok(reservationService.getUserReservations());
    }

    @GetMapping("/expired")
    public ResponseEntity<List<ReservationResponse>> getExpiredReservations() {
        return ResponseEntity.ok(reservationService.getExpiredReservations());
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getBookReservations(@RequestParam Long bookId) {
        return ResponseEntity.ok(reservationService.getBookReservations(bookId));
    }

    @GetMapping("/{reservationId}")
    @PreAuthorize("@reservationAuthorizationService.canViewDetails(#reservationId)")
    public ResponseEntity<ReservationResponse> getReservationDetails(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.getReservationDetails(reservationId));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam Long bookId,
                                                     @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate startDate,
                                                     @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate endDate) {
        return ResponseEntity.ok(reservationService.checkAvailability(bookId, startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@Valid @RequestBody ReservationRequest reservation) {
        return ResponseEntity.ok(reservationService.addReservation(reservation));
    }

    @PatchMapping("/{reservationId}/cancel")
    @PreAuthorize("@reservationAuthorizationService.canCancelReservation(#reservationId)")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<Void> changeReservationStatus(@PathVariable Long reservationId,
                                                        @Valid @RequestBody ReservationStatusUpdateRequest request) {
        reservationService.changeReservationStatus(reservationId, request);
        return ResponseEntity.noContent().build();
    }

}
