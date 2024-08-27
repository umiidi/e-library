package az.company.elibrary.service.user;

import az.company.elibrary.models.enums.Role;
import az.company.elibrary.models.request.ChangePasswordRequest;
import az.company.elibrary.models.request.UserRequest;
import az.company.elibrary.models.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getUserById(Long id);

    UserResponse getUserByEmail(String email);

    List<UserResponse> searchUsersByFullName(String keyword);

    List<UserResponse> getUsersByRole(Role role);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest userRequest);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void assignAdminRole(Long userId);

    void deactivateUser(Long id);

    void deleteUser(Long id);

}
