package az.company.elibrary.security.authorization;

import az.company.elibrary.models.entity.Review;
import az.company.elibrary.models.entity.User;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewAuthorizationService implements AuthorizationService{

    private final CommonGetterService getterService;

    public boolean canDelete(Long entityId) {
        User user = getterService.getAuthenticatedUser();
        return isSuperAdmin(user) || isOwner(entityId, user);
    }

    @Override
    public boolean isOwner(Long entityId, User user) {
        Review review = getterService.getReview(entityId);
        Long loggedUserId = user.getId();
        return review.getUserId().equals(loggedUserId);
    }

}
