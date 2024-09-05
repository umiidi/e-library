package az.company.elibrary.controller;

import az.company.elibrary.models.request.BookRequest;
import az.company.elibrary.models.request.CategoryRequest;
import az.company.elibrary.models.response.BookResponse;
import az.company.elibrary.models.response.CategoryResponse;
import az.company.elibrary.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> get(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> add(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.add(categoryRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.update(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
