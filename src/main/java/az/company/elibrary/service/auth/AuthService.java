package az.company.elibrary.service.auth;

import az.company.elibrary.models.response.SignInResponse;
import az.company.elibrary.models.request.SignInRequest;
import az.company.elibrary.models.request.SignUpRequest;

public interface AuthService {

    SignInResponse login(SignInRequest signInRequest);

    void register(SignUpRequest signUpRequest);

}