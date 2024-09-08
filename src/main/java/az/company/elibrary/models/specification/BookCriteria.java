package az.company.elibrary.models.specification;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BookCriteria {

    private String publisher;

    private Integer minPublishedYear;
    private Integer maxPublishedYear;

    private List<Long> categoriesId;

    private List<Long> genresId;

    private Integer minRating;
    private Integer maxRating;

    private String language;

}
