package com.wolfalone.gamecdbackend.service;

import com.wolfalone.gamecdbackend.dto.CategoryDTO;
import com.wolfalone.gamecdbackend.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryDTO> mapListCateToDTO(List<Category> categoryList);
    ResponseEntity<?> getAllCategories();
}
