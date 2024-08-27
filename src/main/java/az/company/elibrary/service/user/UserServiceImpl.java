package az.company.elibrary.service.user;

import az.company.elibrary.models.entity.User;
import az.company.elibrary.models.enums.Role;
import az.company.elibrary.models.request.ChangePasswordRequest;
import az.company.elibrary.models.request.UserRequest;
import az.company.elibrary.models.response.UserResponse;
import az.company.elibrary.repository.UserRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import az.company.elibrary.service.password.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.elibrary.mapper.UserMapper.USER_MAPPER;
import static az.company.elibrary.models.enums.Role.ROLE_ADMIN;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommonGetterService getterService;
    private final PasswordService passwordService;

    @Override
    public UserResponse getUserById(Long id) {
        User user = getterService.getUser(id);
        return USER_MAPPER.mapToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = getterService.getUser(email);
        return USER_MAPPER.mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> searchUsersByFullName(String keyword) {
        List<User> users = userRepository.findUsersByFullName(keyword);
        return users.stream()
                .map(USER_MAPPER::mapToUserResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(USER_MAPPER::mapToUserResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(USER_MAPPER::mapToUserResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = getterService.getUser(id);
        USER_MAPPER.updateEntity(userRequest, user);
        userRepository.save(user);
        return USER_MAPPER.mapToUserResponse(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        passwordService.checkNewPasswordsEquality(request);
        User user = getterService.getAuthenticatedUser();
        passwordService.validateOldPassword(request.getOldPassword(), user.getPassword());
        user.setPassword(passwordService.encodePassword(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void assignAdminRole(Long userId) {
        User user = getterService.getUser(userId);
        user.setRole(ROLE_ADMIN);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deactivateUser(Long id) {
        User user = getterService.getUser(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

}
