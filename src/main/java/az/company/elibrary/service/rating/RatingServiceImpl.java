package az.company.elibrary.service.rating;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Rating;
import az.company.elibrary.models.entity.User;
import az.company.elibrary.models.request.RatingRequest;
import az.company.elibrary.models.response.RatingResponse;
import az.company.elibrary.repository.RatingRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.elibrary.mapper.RatingMapper.RATING_MAPPER;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final CommonGetterService getterService;

    @Override
    public List<RatingResponse> getRatingsByUser() {
        User user = getterService.getAuthenticatedUser();
        Long userId = user.getId();
        return ratingRepository.findByUserId(userId).stream()
                .map(RATING_MAPPER::mapToRatingResponse)
                .toList();
    }

    @Override
    public double getAverageRating(Long bookId) {
        List<Rating> ratings = ratingRepository.findByBookId(bookId);
        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0);
    }

    @Override
    public RatingResponse addOrUpdateRating(RatingRequest ratingRequest) {
        Book book = getterService.getBook(ratingRequest.getBookId());
        Long userId = getterService.getAuthenticatedUser().getId();
        Rating ratingEntity = ratingRepository.findByUserIdAndBookId(userId, book.getId())
                .map(rating -> {
                    rating.setRating(ratingRequest.getRating());
                    return rating;
                })
                .orElse(RATING_MAPPER.mapToRatingEntity(ratingRequest, userId, book));
        ratingRepository.save(ratingEntity);
        return RATING_MAPPER.mapToRatingResponse(ratingEntity);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

}
