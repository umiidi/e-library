package az.company.elibrary.models.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static az.company.elibrary.models.constants.BookConstants.MAX_RATING;
import static az.company.elibrary.models.constants.BookConstants.MIN_RATING;

@Data
public class RatingRequest {

    @NotNull
    Long bookId;

    @Min(MIN_RATING)
    @Max(MAX_RATING)
    int rating;

}
