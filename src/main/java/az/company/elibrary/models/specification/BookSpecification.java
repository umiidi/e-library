package az.company.elibrary.models.specification;

import az.company.elibrary.models.entity.Book;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> filter(BookCriteria bookCriteria) {
        return (root, query, criteriaBuilder) -> {
            root.fetch("author", JoinType.LEFT);
            List<Predicate> list = new ArrayList<>();

            if (bookCriteria.getMinPublishedYear() != null) {
                list.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get("year"), bookCriteria.getMinPublishedYear()));
            }
            if (bookCriteria.getMaxPublishedYear() != null) {
                list.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get("year"), bookCriteria.getMinPublishedYear()));
            }
            return criteriaBuilder.and(list.toArray(Predicate[]::new));
        };
    }

    public static Specification<Book> search(String keyword) {
        return (root, query, builder) -> {
            root.fetch("author", JoinType.LEFT);

            final String matchValue = String.format("%s%s%s", "%", keyword.toLowerCase(), "%");
            return builder.like(builder.lower(root.get("title")), matchValue);
        };
    }

}
