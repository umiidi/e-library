package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Reservation;
import az.company.elibrary.models.request.ReservationRequest;
import az.company.elibrary.models.response.ReservationResponse;

import static az.company.elibrary.models.enums.ReservationStatus.PENDING;

public enum ReservationMapper {

    RESERVATION_MAPPER;

    public Reservation mapToReservationEntity(ReservationRequest reservationRequest, Long userId, Book book) {
        return Reservation.builder()
                .userId(userId)
                .book(book)
                .status(PENDING)
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .build();
    }

    public ReservationResponse mapToReservationResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .bookId(reservation.getBook().getId())
                .status(reservation.getStatus())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .build();
    }

}
