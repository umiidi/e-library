package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @EntityGraph(attributePaths = {"author"})
    List<Book> findAll();

    void deleteBookByIdAndAuthorId(Long id, Long authorId);

}
