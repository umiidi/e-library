package az.company.elibrary.models.specification;

import lombok.Data;

@Data
public class BookCriteria {

    private String category;
    private Integer minPublishedYear;
    private Integer maxPublishedYear;

}
