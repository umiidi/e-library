package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @EntityGraph(attributePaths = {"books"})
    Optional<Author> findById(Long authorId);

    @Query("SELECT a FROM Author a WHERE a.firstName LIKE %:keyword% OR a.lastName LIKE %:keyword%")
    List<Author> findAuthorsByKeyword(String keyword);

}
