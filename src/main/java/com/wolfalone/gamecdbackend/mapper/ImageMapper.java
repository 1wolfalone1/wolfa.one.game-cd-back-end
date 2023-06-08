package com.wolfalone.gamecdbackend.mapper;

import com.wolfalone.gamecdbackend.dto.CategoryDTO;
import com.wolfalone.gamecdbackend.dto.ImageDTO;
import com.wolfalone.gamecdbackend.entity.Category;
import com.wolfalone.gamecdbackend.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface ImageMapper {
    @Mapping(target = "id", source = "image.id")
    @Mapping(target = "imgUrl", source = "image.imageUrl")
    ImageDTO toDTO(Image image);
}
