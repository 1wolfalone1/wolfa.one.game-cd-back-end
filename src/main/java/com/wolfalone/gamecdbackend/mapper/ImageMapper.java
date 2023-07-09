package com.wolfalone.gamecdbackend.mapper;

import com.wolfalone.gamecdbackend.dto.CategoryDTO;
import com.wolfalone.gamecdbackend.dto.ImageDTO;
import com.wolfalone.gamecdbackend.dto.ImageUpdateDTO;
import com.wolfalone.gamecdbackend.entity.Category;
import com.wolfalone.gamecdbackend.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ImageMapper {
    @Mapping(target = "id", source = "image.id")
    @Mapping(target = "imgUrl", source = "image.imageUrl")
    ImageDTO toDTO(Image image);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "src", source = "imageUrl")
    ImageUpdateDTO toImageUpdateDTO(Image image);

    List<ImageUpdateDTO> toListImageUpdateDTO(List<Image> imagesEntity);
}
