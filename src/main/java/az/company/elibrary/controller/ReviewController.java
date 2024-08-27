package az.company.elibrary.controller;

import az.company.elibrary.models.request.ReviewRequest;
import az.company.elibrary.models.response.ReviewResponse;
import az.company.elibrary.service.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponse>> getReviewsByAuthenticatedUser() {
        return ResponseEntity.ok(reviewService.getReviewsByUser());
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviewsForBook(@RequestParam Long bookId) {
        return ResponseEntity.ok(reviewService.getAllReviewsForBook(bookId));
    }

    @PutMapping
    public ResponseEntity<ReviewResponse> addOrUpdateReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewService.addOrUpdateReview(reviewRequest));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long reviewId) {
        reviewService.deleteRating(reviewId);
        return ResponseEntity.noContent().build();
    }

}
