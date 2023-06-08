package com.wolfalone.gamecdbackend.controller;

import com.wolfalone.gamecdbackend.config.constant.ApiConstant;
import com.wolfalone.gamecdbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = ApiConstant.FRONT_END_URL, allowCredentials = "true")
public class CategoryController {
@Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseEntity<?> getGamesPaging(){
        return categoryService.getAllCategories();
    }



}
