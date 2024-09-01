package az.company.elibrary.security.authorization;

import az.company.elibrary.models.entity.User;

import static az.company.elibrary.models.enums.Role.ROLE_SUPER_ADMIN;

public interface AuthorizationService {

    boolean isOwner(Long entityId, User user);

    default boolean isSuperAdmin(User user) {
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ROLE_SUPER_ADMIN.name()));
    }

}
