package az.company.elibrary.service.category;

import az.company.elibrary.models.entity.Category;
import az.company.elibrary.models.request.CategoryRequest;
import az.company.elibrary.models.response.CategoryResponse;
import az.company.elibrary.repository.CategoryRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.elibrary.mapper.CategoryMapper.CATEGORY_MAPPER;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CommonGetterService getterService;

    @Override
    public CategoryResponse get(Long id) {
        Category category = getterService.getCategory(id);
        return CATEGORY_MAPPER.mapToCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(CATEGORY_MAPPER::mapToCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse add(CategoryRequest categoryRequest) {
        Category category = CATEGORY_MAPPER.mapToCategoryEntity(categoryRequest);
        categoryRepository.save(category);
        return CATEGORY_MAPPER.mapToCategoryResponse(category);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = getterService.getCategory(id);
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return CATEGORY_MAPPER.mapToCategoryResponse(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
