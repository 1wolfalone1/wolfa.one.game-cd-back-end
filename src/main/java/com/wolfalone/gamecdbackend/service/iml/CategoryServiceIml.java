package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.dto.CategoryDTO;
import com.wolfalone.gamecdbackend.entity.Category;
import com.wolfalone.gamecdbackend.mapper.CategoryMapper;
import com.wolfalone.gamecdbackend.repository.CategoryRepo;
import com.wolfalone.gamecdbackend.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceIml implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepo categoryRepo;
    @Override
    public List<CategoryDTO> mapListCateToDTO(List<Category> categoryList) {

        return categoryList.stream().map(categoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(mapListCateToDTO(categoryRepo.findAll()));
    }
}
