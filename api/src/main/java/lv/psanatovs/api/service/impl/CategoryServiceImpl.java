package lv.psanatovs.api.service.impl;

import lv.psanatovs.api.dto.CategoryDTO;
import lv.psanatovs.api.entity.Category;
import lv.psanatovs.api.repository.CategoryRepository;
import lv.psanatovs.api.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(this::modelToDto)
                .toList();
    }

    private CategoryDTO modelToDto(Category category) {
        return new CategoryDTO(
                category.getName()
        );
    }
}
