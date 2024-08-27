package az.company.elibrary.models.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank
    private String title;

    @NotNull
    private Long authorId;

    @NotBlank
    private String isbn;

    @NotBlank
    private String publisher;

    @NotNull
    private Integer publicationYear;

    @Min(value = 0)
    private int inventory;

}
