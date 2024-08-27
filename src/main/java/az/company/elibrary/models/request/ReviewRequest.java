package az.company.elibrary.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequest {

    @NotNull
    Long bookId;

    @NotBlank
    String reviewText;

}
