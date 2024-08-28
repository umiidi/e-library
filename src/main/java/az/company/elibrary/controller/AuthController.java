package az.company.elibrary.controller;

import az.company.elibrary.models.request.RefreshTokenRequest;
import az.company.elibrary.models.response.AuthResponse;
import az.company.elibrary.models.request.SignInRequest;
import az.company.elibrary.models.request.SignUpRequest;
import az.company.elibrary.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody SignInRequest signInRequest) {
        AuthResponse authResponse = authService.login(signInRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.register(signUpRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse authResponse = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> signOut() {
        authService.logout();
        return ResponseEntity.noContent().build();
    }

}
