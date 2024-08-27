package az.company.elibrary.service.password;

import az.company.elibrary.models.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public void validateOldPassword(String oldPasswordFromRequest, String currentPassword) {
        if (!passwordEncoder.matches(oldPasswordFromRequest, currentPassword)) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }

    public void checkNewPasswordsEquality(ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
