package az.company.elibrary.service.review;

import az.company.elibrary.models.request.ReviewRequest;
import az.company.elibrary.models.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    List<ReviewResponse> getReviewsByUser();

    List<ReviewResponse> getAllReviewsForBook(Long bookId);

    ReviewResponse addOrUpdateReview(ReviewRequest reviewRequest);

    void deleteRating(Long reviewId);

}
