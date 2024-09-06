package az.company.elibrary.models.enums;


import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum CustomSortBy {

    AVERAGE_RATING(Sort.Order.asc("averageRating")),
    NEWEST(Sort.Order.asc("book.")),
    OLDEST(Sort.Order.asc("averageRating")),
    MOST_BORROWED(Sort.Order.asc("averageRating")),
    MOST_RESERVED(Sort.Order.asc("averageRating"));

    private final Sort.Order order;

    CustomSortBy(Sort.Order order) {
        this.order = order;
    }

}
