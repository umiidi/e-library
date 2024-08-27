package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Review;
import az.company.elibrary.models.request.ReviewRequest;
import az.company.elibrary.models.response.ReviewResponse;

public enum ReviewMapper {

    REVIEW_MAPPER;

    public Review mapToReviewEntity(ReviewRequest reviewRequest, Long userId, Book book) {
        return Review.builder()
                .userId(userId)
                .text(reviewRequest.getReviewText())
                .book(book)
                .build();
    }

    public ReviewResponse mapToReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .bookId(review.getBook().getId())
                .reviewText(review.getText())
                .build();
    }

}
