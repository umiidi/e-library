package az.company.elibrary.service.auth;

import az.company.elibrary.models.entity.User;
import az.company.elibrary.models.response.SignInResponse;
import az.company.elibrary.models.request.SignInRequest;
import az.company.elibrary.models.request.SignUpRequest;
import az.company.elibrary.models.enums.Role;
import az.company.elibrary.repository.UserRepository;
import az.company.elibrary.security.service.JwtService;
import az.company.elibrary.service.password.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static az.company.elibrary.mapper.UserMapper.USER_MAPPER;
import static az.company.elibrary.models.enums.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Role DEFAULT_USER_ROLE = ROLE_USER;

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordService passwordService;

    @Override
    public SignInResponse login(SignInRequest signInRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        String token = jwtService.issueToken(authentication);
        return new SignInResponse(token);
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

}
