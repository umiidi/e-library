package az.company.elibrary.service.auth;

import az.company.elibrary.exception.type.AccountInvalidException;
import az.company.elibrary.models.entity.User;
import az.company.elibrary.models.request.RefreshTokenRequest;
import az.company.elibrary.models.response.AuthResponse;
import az.company.elibrary.models.request.SignInRequest;
import az.company.elibrary.models.request.SignUpRequest;
import az.company.elibrary.models.enums.Role;
import az.company.elibrary.repository.UserRepository;
import az.company.elibrary.security.service.AccessTokenService;
import az.company.elibrary.security.service.RefreshTokenService;
import az.company.elibrary.service.getter.CommonGetterService;
import az.company.elibrary.service.password.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static az.company.elibrary.mapper.UserMapper.USER_MAPPER;
import static az.company.elibrary.models.enums.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Role DEFAULT_USER_ROLE = ROLE_USER;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordService passwordService;
    private final CommonGetterService getterService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse login(SignInRequest signInRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        return buildAuthResponse(authentication);
    }

    @Override
    public void register(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("email already exist");
        }

        String encryptedPassword = passwordService.encodePassword((signUpRequest.getPassword()));
        User user = USER_MAPPER.mapToUserEntity(signUpRequest, encryptedPassword, DEFAULT_USER_ROLE);
        userRepository.save(user);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String email = refreshTokenService.read(request.getRefreshToken());
        User user = getterService.getUser(email);
        validateAccountStatus(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword());
        return buildAuthResponse(authentication);
    }

    @Override
    public void logout() {
        User user = getterService.getAuthenticatedUser();
        String email = user.getEmail();
        refreshTokenService.delete(email);
    }

    private AuthResponse buildAuthResponse(Authentication authentication) {
        String accessToken = accessTokenService.create(authentication);
        String email = authentication.getName();
        String refreshToken = refreshTokenService.create(email);
        return new AuthResponse(accessToken, refreshToken);
    }

    private void validateAccountStatus(UserDetails userDetails) {
        boolean isActive = userDetails.isEnabled()
                && userDetails.isAccountNonLocked()
                && userDetails.isAccountNonExpired()
                && userDetails.isCredentialsNonExpired();
        if (!isActive) {
            throw new AccountInvalidException("Account is deactivated");
        }
    }

}
