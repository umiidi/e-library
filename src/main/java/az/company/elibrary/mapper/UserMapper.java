package az.company.elibrary.mapper;

import az.company.elibrary.models.request.SignUpRequest;
import az.company.elibrary.models.entity.User;
import az.company.elibrary.models.enums.Role;
import az.company.elibrary.models.request.UserRequest;
import az.company.elibrary.models.response.UserResponse;

public enum UserMapper {

    USER_MAPPER;

    public User mapToUserEntity(SignUpRequest signUpRequest, String encryptedPassword, Role defaultRole) {
        return User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(encryptedPassword)
                .role(defaultRole)
                .enabled(true)
                .build();
    }

    public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    public void updateEntity(UserRequest userRequest, User user) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
    }

}
