package az.company.elibrary.security.authorization;

import az.company.elibrary.models.entity.Reservation;
import az.company.elibrary.models.entity.User;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationAuthorizationService implements AuthorizationService {

    private final CommonGetterService getterService;

    public boolean canViewDetails(Long entityId){
        User user = getterService.getAuthenticatedUser();
        return isSuperAdmin(user) || isOwner(entityId, user);
    }

    public boolean canCancelReservation(Long entityId) {
        User user = getterService.getAuthenticatedUser();
        return isOwner(entityId, user);
    }

    @Override
    public boolean isOwner(Long entityId, User user) {
        Reservation reservation = getterService.getReservation(entityId);
        Long loggedUserId = user.getId();
        return reservation.getUserId().equals(loggedUserId);
    }

}
