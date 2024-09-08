package az.company.elibrary.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private Author author;

    private String isbn;

    private String publisher;

    private Integer publicationYear;

    private int inventory;

    private String language;

    @ManyToOne
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Genre> genres;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Rating> ratings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Reservation> reservations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Borrowing> borrowings;

}
