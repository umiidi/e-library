package az.company.elibrary.service.auth;

import az.company.elibrary.models.request.RefreshTokenRequest;
import az.company.elibrary.models.response.AuthResponse;
import az.company.elibrary.models.request.SignInRequest;
import az.company.elibrary.models.request.SignUpRequest;

public interface AuthService {

    AuthResponse login(SignInRequest signInRequest);

    void register(SignUpRequest signUpRequest);

    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void logout();

}