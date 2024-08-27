package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Borrowing;
import az.company.elibrary.models.entity.User;
import az.company.elibrary.models.enums.BorrowingStatus;
import az.company.elibrary.models.request.BorrowingRequest;
import az.company.elibrary.models.response.BorrowingResponse;

public enum BorrowingMapper {

    BORROWING_MAPPER;

    public Borrowing mapToBorrowingEntity(BorrowingRequest request, User user, Book book) {
        return Borrowing.builder()
                .user(user)
                .book(book)
                .status(BorrowingStatus.BORROWED)
                .borrowDate(request.getBorrowDate())
                .dueDate(request.getDueDate())
                .build();
    }

    public BorrowingResponse mapToBorrowingResponse(Borrowing borrowing) {
        return BorrowingResponse.builder()
                .id(borrowing.getId())
                .userId(borrowing.getUser().getId())
                .bookId(borrowing.getBook().getId())
                .status(borrowing.getStatus())
                .borrowDate(borrowing.getBorrowDate())
                .dueDate(borrowing.getDueDate())
                .returnDate(borrowing.getReturnDate())
                .build();
    }

}
