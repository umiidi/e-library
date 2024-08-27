package az.company.elibrary.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private Long authorId;
    private String isbn;
    private String publisher;
    private Integer publicationYear;
    private int inventory;

}
