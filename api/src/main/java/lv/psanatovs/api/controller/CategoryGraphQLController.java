package lv.psanatovs.api.controller;

import lv.psanatovs.api.dto.CategoryDTO;
import lv.psanatovs.api.service.CategoryService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CategoryGraphQLController {
    private final CategoryService categoryService;

    public CategoryGraphQLController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
