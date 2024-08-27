package az.company.elibrary.service.borrowing;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Borrowing;
import az.company.elibrary.models.entity.User;
import az.company.elibrary.models.enums.BorrowingStatus;
import az.company.elibrary.models.request.BorrowingRequest;
import az.company.elibrary.models.request.BorrowingStatusUpdateRequest;
import az.company.elibrary.models.response.BorrowingResponse;
import az.company.elibrary.repository.BorrowingRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static az.company.elibrary.mapper.BorrowingMapper.BORROWING_MAPPER;

@Service
@RequiredArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final CommonGetterService getterService;

    @Override
    public BorrowingResponse getBorrowingById(Long id) {
        Borrowing borrowing = getterService.getBorrowing(id);
        return BORROWING_MAPPER.mapToBorrowingResponse(borrowing);
    }

    @Override
    public List<BorrowingResponse> getMyBorrowings() {
        Long userId = getterService.getAuthenticatedUser().getId();
        return borrowingRepository.findAllByUserId(userId).stream()
                .map(BORROWING_MAPPER::mapToBorrowingResponse)
                .toList();
    }

    @Override
    public List<BorrowingResponse> getAllBorrowings() {
        return borrowingRepository.findAll().stream()
                .map(BORROWING_MAPPER::mapToBorrowingResponse)
                .toList();
    }

    @Override
    public BorrowingResponse borrowBook(BorrowingRequest borrowingRequest) {
        User user = getterService.getUser(borrowingRequest.getUserId());
        Book book = getterService.getBook(borrowingRequest.getBookId());
        Borrowing borrowing = BORROWING_MAPPER.mapToBorrowingEntity(borrowingRequest, user, book);
        borrowingRepository.save(borrowing);
        return BORROWING_MAPPER.mapToBorrowingResponse(borrowing);
    }

    @Override
    public void returnBook(Long borrowingId) {
        Borrowing borrowing = getterService.getBorrowing(borrowingId);
        borrowing.setStatus(BorrowingStatus.RETURNED);
        borrowing.setReturnDate(LocalDate.now());
        borrowingRepository.save(borrowing);
    }

    @Override
    public void changeBorrowingStatus(Long borrowingId, BorrowingStatusUpdateRequest updateStatusRequest) {
        Borrowing borrowing = getterService.getBorrowing(borrowingId);
        borrowing.setStatus(updateStatusRequest.getStatus());
        borrowingRepository.save(borrowing);
    }

}
