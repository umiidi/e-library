package az.company.elibrary.models.specification;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.entity.Category;
import az.company.elibrary.models.entity.Genre;
import az.company.elibrary.models.entity.Rating;
import az.company.elibrary.models.enums.BookSortBy;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static az.company.elibrary.models.constants.BookConstants.MAX_RATING;
import static az.company.elibrary.models.constants.BookConstants.MIN_RATING;

public class BookSpecification {

    public static Specification<Book> filter(BookCriteria criteria, BookSortBy sortBy) {
        return (root, query, criteriaBuilder) -> {

            // specifications
            List<Predicate> list = new ArrayList<>(10);

            if (isNotNull(criteria.getPublisher())) {
                final String matchValue = String.format("%s%s%s", "%", criteria.getPublisher().toLowerCase(), "%");
                list.add(criteriaBuilder
                        .like(criteriaBuilder.lower(root.get(Book.Fields.publisher)), matchValue));
            }

            if (isNotNull(criteria.getMinPublishedYear())) {
                list.add(criteriaBuilder
                        .greaterThanOrEqualTo(root.get(Book.Fields.publicationYear), criteria.getMinPublishedYear()));
            }

            if (isNotNull(criteria.getMaxPublishedYear())) {
                list.add(criteriaBuilder
                        .lessThanOrEqualTo(root.get(Book.Fields.publicationYear), criteria.getMaxPublishedYear()));
            }

            if (isNotNullAndNotEmpty(criteria.getCategoriesId())) {
                list.add(criteriaBuilder
                        .in(root.get(Book.Fields.category).get(Category.Fields.id)).value(criteria.getCategoriesId()));
            }

            if (isNotNullAndNotEmpty(criteria.getGenresId())) {
                list.add(root.get(Book.Fields.genres).get(Genre.Fields.id).in(criteria.getGenresId()));
                /*
                    todo: research: why criteriaBuilder is not working properly?
                 For example, bookId = 1, genreIds = [1, 2, 3] AND we're searching for GenreId 2,
                 it would return BookId 1 but only show GenreId 2 in the result, omitting other genres like 1 and 3.
                 */
            }

            Expression<Double> averageRating = criteriaBuilder.avg(root.get(Book.Fields.ratings).get(Rating.Fields.rating));

            double minRating = isNotNull(criteria.getMinRating()) ? criteria.getMinRating().doubleValue() : MIN_RATING;
            double maxRating = isNotNull(criteria.getMaxRating()) ? criteria.getMaxRating().doubleValue() : MAX_RATING;

            query.having(criteriaBuilder
                    .between(averageRating, minRating, maxRating));

            if (isNotNull(criteria.getLanguage())) {
                list.add(criteriaBuilder
                        .equal(root.get(Book.Fields.language), criteria.getLanguage()));
            }

            // sorting
            Order order = switch (sortBy) {
                case AVERAGE_RATING -> criteriaBuilder.desc(averageRating);
                case NEWEST -> criteriaBuilder.desc(root.get(Book.Fields.publicationYear));
                case OLDEST -> criteriaBuilder.asc(root.get(Book.Fields.publicationYear));
                case MOST_BORROWED -> criteriaBuilder.desc(criteriaBuilder.size(root.get(Book.Fields.borrowings)));
                case MOST_RESERVED -> criteriaBuilder.desc(criteriaBuilder.size(root.get(Book.Fields.reservations)));
            };

            query.groupBy(root);
            query.orderBy(order);

            return criteriaBuilder.and(list.toArray(Predicate[]::new));
        };
    }

    public static Specification<Book> search(String keyword) {
        return (root, query, builder) -> {
            root.fetch("author", JoinType.LEFT);
            root.fetch("category", JoinType.LEFT);
            root.fetch("genres", JoinType.LEFT);

            final String matchValue = String.format("%s%s%s", "%", keyword.toLowerCase(), "%");
            return builder.like(builder.lower(root.get("title")), matchValue);
        };
    }

    private static boolean isNotNull(Object object) {
        return object != null;
    }

    private static boolean isNotNullAndNotEmpty(List<?> list) {
        return isNotNull(list) && !list.isEmpty();
    }

}
