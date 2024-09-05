package az.company.elibrary.service.category;

import az.company.elibrary.models.request.CategoryRequest;
import az.company.elibrary.models.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse get(Long id);

    List<CategoryResponse> getAll();

    CategoryResponse add(CategoryRequest categoryRequest);

    CategoryResponse update(Long id, CategoryRequest categoryRequest);

    void delete(Long id);

}
