package az.company.elibrary.models.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {

    @NotNull
    Long bookId;

    @Min(0)
    @Max(5)
    int rating;

}
