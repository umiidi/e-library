package az.company.elibrary.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Author author;

    private String isbn;

    private String publisher;

    private Integer publicationYear;

    private int inventory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private Set<Rating> ratings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private Set<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private Set<Reservation> reservations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private Set<Borrowing> borrowings;

}
