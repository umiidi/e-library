package az.company.elibrary.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private LocalDate dateOfDeath;

    @Column(length = 1000, nullable = false)
    private String biography;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Book> books;

}
