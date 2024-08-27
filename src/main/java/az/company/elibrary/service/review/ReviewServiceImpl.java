package az.company.elibrary.service.review;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Review;
import az.company.elibrary.models.request.ReviewRequest;
import az.company.elibrary.models.response.ReviewResponse;
import az.company.elibrary.repository.ReviewRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.elibrary.mapper.ReviewMapper.REVIEW_MAPPER;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CommonGetterService getterService;

    @Override
    public List<ReviewResponse> getReviewsByUser() {
        Long userId = getterService.getAuthenticatedUser().getId();
        return reviewRepository.findByUserId(userId).stream()
                .map(REVIEW_MAPPER::mapToReviewResponse)
                .toList();
    }

    @Override
    public List<ReviewResponse> getAllReviewsForBook(Long bookId) {
        return reviewRepository.findByBookId(bookId).stream()
                .map(REVIEW_MAPPER::mapToReviewResponse)
                .toList();
    }

    @Override
    public ReviewResponse addOrUpdateReview(ReviewRequest reviewRequest) {
        Book book = getterService.getBook(reviewRequest.getBookId());
        Long userId = getterService.getAuthenticatedUser().getId();
        Review reviewEntity = reviewRepository.findByUserIdAndBookId(userId, book.getId())
                .map(review -> {
                    review.setText(reviewRequest.getReviewText());
                    return review;
                })
                .orElse(REVIEW_MAPPER.mapToReviewEntity(reviewRequest, userId, book));
        reviewRepository.save(reviewEntity);
        return REVIEW_MAPPER.mapToReviewResponse(reviewEntity);
    }

    @Override
    public void deleteRating(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}