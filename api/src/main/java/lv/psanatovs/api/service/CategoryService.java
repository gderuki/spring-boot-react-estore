package lv.psanatovs.api.service;

import lv.psanatovs.api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
}
