package az.company.elibrary.controller;

import az.company.elibrary.models.response.SignInResponse;
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
    public ResponseEntity<SignInResponse> login(@Valid @RequestBody SignInRequest signInRequest) {
        SignInResponse signInResponse = authService.login(signInRequest);
        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.register(signUpRequest);
        return ResponseEntity.noContent().build();
    }

}
