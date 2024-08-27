package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Rating;
import az.company.elibrary.models.request.RatingRequest;
import az.company.elibrary.models.response.RatingResponse;

public enum RatingMapper {

    RATING_MAPPER;

    public Rating mapToRatingEntity(RatingRequest ratingRequest, Long userId, Book book) {
        return Rating.builder()
                .userId(userId)
                .rating(ratingRequest.getRating())
                .book(book)
                .build();
    }

    public RatingResponse mapToRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .userId(rating.getUserId())
                .bookId(rating.getBook().getId())
                .rating(rating.getRating())
                .build();
    }

}
