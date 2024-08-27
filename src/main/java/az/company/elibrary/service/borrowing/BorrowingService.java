package az.company.elibrary.service.borrowing;

import az.company.elibrary.models.request.BorrowingRequest;
import az.company.elibrary.models.request.BorrowingStatusUpdateRequest;
import az.company.elibrary.models.response.BorrowingResponse;

import java.util.List;

public interface BorrowingService {

    BorrowingResponse getBorrowingById(Long id);

    List<BorrowingResponse> getMyBorrowings();

    List<BorrowingResponse> getAllBorrowings();

    BorrowingResponse borrowBook(BorrowingRequest borrowingRequest);

    void returnBook(Long borrowingId);

    void changeBorrowingStatus(Long borrowingId, BorrowingStatusUpdateRequest status);

}
