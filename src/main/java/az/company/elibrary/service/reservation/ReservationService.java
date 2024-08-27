package az.company.elibrary.service.reservation;

import az.company.elibrary.models.request.ReservationRequest;
import az.company.elibrary.models.request.ReservationStatusUpdateRequest;
import az.company.elibrary.models.response.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    List<ReservationResponse> getUserReservations();

    List<ReservationResponse> getExpiredReservations();

    List<ReservationResponse> getBookReservations(Long bookId);

    ReservationResponse getReservationDetails(Long reservationId);

    Boolean checkAvailability(Long bookId, LocalDate startDate, LocalDate endDate);

    ReservationResponse addReservation(ReservationRequest reservationRequest);

    void cancelReservation(Long reservationId);

    void changeReservationStatus(Long reservationId, ReservationStatusUpdateRequest request);

}