package az.company.elibrary.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private String description;
    private Long authorId;
    private String isbn;
    private String publisher;
    private Integer publicationYear;
    private int inventory;
    private String language;

    private CategoryResponse category;
    private List<GenreResponse> genres;

}
