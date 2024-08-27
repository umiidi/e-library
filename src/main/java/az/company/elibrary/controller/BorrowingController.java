package az.company.elibrary.controller;

import az.company.elibrary.models.request.BorrowingRequest;
import az.company.elibrary.models.request.BorrowingStatusUpdateRequest;
import az.company.elibrary.models.response.BorrowingResponse;
import az.company.elibrary.service.borrowing.BorrowingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowings")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingResponse> getBorrowingById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.getBorrowingById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<BorrowingResponse>> getMyBorrowings() {
        return ResponseEntity.ok(borrowingService.getMyBorrowings());
    }

    @GetMapping
    public ResponseEntity<List<BorrowingResponse>> getAllBorrowings() {
        return ResponseEntity.ok(borrowingService.getAllBorrowings());
    }

    @PostMapping
    public ResponseEntity<BorrowingResponse> borrowBook(@Valid @RequestBody BorrowingRequest borrowingRequest) {
        return ResponseEntity.ok(borrowingService.borrowBook(borrowingRequest));
    }

    @PutMapping("/{borrowingId}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long borrowingId) {
        borrowingService.returnBook(borrowingId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{borrowingId}/change-status")
    public ResponseEntity<Void> changeBorrowingStatus(@PathVariable Long borrowingId,
                                                      @RequestBody BorrowingStatusUpdateRequest status) {
        borrowingService.changeBorrowingStatus(borrowingId, status);
        return ResponseEntity.noContent().build();
    }

}
