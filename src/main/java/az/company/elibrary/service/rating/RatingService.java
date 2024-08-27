package az.company.elibrary.service.rating;

import az.company.elibrary.models.request.RatingRequest;
import az.company.elibrary.models.response.RatingResponse;

import java.util.List;

public interface RatingService {

    List<RatingResponse> getRatingsByUser();

    double getAverageRating(Long bookId);

    RatingResponse addOrUpdateRating(RatingRequest ratingRequest);

    void deleteRating(Long ratingId);

}
