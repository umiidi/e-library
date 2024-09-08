package az.company.elibrary.repository;

import az.company.elibrary.models.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    void deleteBookByIdAndAuthorId(Long id, Long authorId);

    @EntityGraph(attributePaths = {"author", "category", "genres"})
    List<Book> findByAuthorId(Long authorId);

    @Override
    @EntityGraph(attributePaths = {"author", "category", "genres"})
    List<Book> findAll();

    @Override
    @EntityGraph(attributePaths = {"author", "category", "genres"})
    Page<Book> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"author", "category", "genres"})
    Page<Book> findAll(Specification<Book> spec, Pageable pageable);

}
