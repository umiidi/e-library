package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Category;
import az.company.elibrary.models.request.CategoryRequest;
import az.company.elibrary.models.response.CategoryResponse;

public enum CategoryMapper {

    CATEGORY_MAPPER;

    public Category mapToCategoryEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }

    public CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
