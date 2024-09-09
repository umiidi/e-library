package az.company.elibrary.service.reservation;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Reservation;
import az.company.elibrary.models.enums.ReservationStatus;
import az.company.elibrary.models.request.ReservationRequest;
import az.company.elibrary.models.request.ReservationStatusUpdateRequest;
import az.company.elibrary.models.response.ReservationResponse;
import az.company.elibrary.repository.ReservationRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static az.company.elibrary.mapper.ReservationMapper.RESERVATION_MAPPER;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CommonGetterService getterService;

    @Override
    public List<ReservationResponse> getUserReservations() {
        Long userId = getterService.getAuthenticatedUser().getId();
        return reservationRepository.findByUserId(userId).stream()
                .map(RESERVATION_MAPPER::mapToReservationResponse)
                .toList();
    }

    @Override
    public List<ReservationResponse> getExpiredReservations() {
        return reservationRepository.findExpiredReservations().stream()
                .map(RESERVATION_MAPPER::mapToReservationResponse)
                .toList();
    }

    @Override
    public List<ReservationResponse> getBookReservations(Long bookId) {
        return reservationRepository.findByBookId(bookId).stream()
                .map(RESERVATION_MAPPER::mapToReservationResponse)
                .toList();
    }

    @Override
    public ReservationResponse getReservationDetails(Long reservationId) {
        Reservation reservation = getterService.getReservation(reservationId);
        return RESERVATION_MAPPER.mapToReservationResponse(reservation);
    }

    @Override
    public Boolean checkAvailability(Long bookId, LocalDate startDate, LocalDate endDate) {
        return reservationRepository.isSuitableForReservation(bookId, startDate, endDate);
    }

    @Override
    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        Book book = getterService.getBook(reservationRequest.getBookId());
        Long userId = getterService.getAuthenticatedUser().getId();
        if (!this.checkAvailability(book.getId(), reservationRequest.getStartDate(), reservationRequest.getEndDate())) {
            throw new IllegalArgumentException("reservation failed");
        }
        Reservation reservation = RESERVATION_MAPPER.mapToReservationEntity(reservationRequest, userId, book);
        reservationRepository.save(reservation);
        return RESERVATION_MAPPER.mapToReservationResponse(reservation);
    }

    @Override
    public void cancelReservation(Long reservationId) {
        Reservation reservation = getterService.getReservation(reservationId);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    @Override
    public void changeReservationStatus(Long reservationId, ReservationStatusUpdateRequest request) {
        Reservation reservation = getterService.getReservation(reservationId);
        reservation.setStatus(request.getStatus());
        reservationRepository.save(reservation);
    }

}
