package az.company.elibrary.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    String accessToken;
    String refreshToken;

}
