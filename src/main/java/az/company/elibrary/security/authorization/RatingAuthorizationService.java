package az.company.elibrary.security.authorization;

import az.company.elibrary.models.entity.Rating;
import az.company.elibrary.models.entity.User;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingAuthorizationService implements AuthorizationService {

    private final CommonGetterService getterService;

    public boolean canDelete(Long entityId) {
        User user = getterService.getAuthenticatedUser();
        return isSuperAdmin(user) || isOwner(entityId, user);
    }

    @Override
    public boolean isOwner(Long entityId, User user) {
        Rating rating = getterService.getRating(entityId);
        Long loggedUserId = user.getId();
        return rating.getUserId().equals(loggedUserId);
    }

}
