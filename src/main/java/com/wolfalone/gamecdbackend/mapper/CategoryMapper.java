package com.wolfalone.gamecdbackend.mapper;

import com.wolfalone.gamecdbackend.dto.CategoryDTO;
import com.wolfalone.gamecdbackend.dto.GameCardDTO;
import com.wolfalone.gamecdbackend.entity.Category;
import com.wolfalone.gamecdbackend.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface CategoryMapper {
    @Mapping(target = "id", source = "category.id")
    @Mapping(target = "name", source = "category.name")
    CategoryDTO toDTO(Category category);
}
