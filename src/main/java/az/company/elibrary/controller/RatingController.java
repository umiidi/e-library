package az.company.elibrary.controller;

import az.company.elibrary.models.request.RatingRequest;
import az.company.elibrary.models.response.RatingResponse;
import az.company.elibrary.service.rating.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/me")
    public ResponseEntity<List<RatingResponse>> getRatingsByAuthenticatedUser() {
        return ResponseEntity.ok(ratingService.getRatingsByUser());
    }

    @GetMapping()
    public ResponseEntity<Double> getAverageRatingForBook(@RequestParam Long bookId) {
        return ResponseEntity.ok(ratingService.getAverageRating(bookId));
    }

    @PutMapping
    public ResponseEntity<RatingResponse> addOrUpdateRating(@Valid @RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.ok(ratingService.addOrUpdateRating(ratingRequest));
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

}
