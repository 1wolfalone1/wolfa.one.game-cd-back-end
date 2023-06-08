package com.wolfalone.gamecdbackend.mapper;

import com.wolfalone.gamecdbackend.dto.*;
import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface GameMapper {

    @Mapping(target = "id", source = "game.id")
    @Mapping(target = "name", source = "game.name")
    @Mapping(target = "imageDTOList", source = "imageDTOList")
    @Mapping(target = "categoryDTOList", source = "categoryDTOList")
    @Mapping(target = "price", source = "game.price")
    @Mapping(target = "quantity", source = "game.quantity")
    @Mapping(target = "description", source = "game.description")
    GameDetailsDTO toDetailsDTO(Game game, List<ImageDTO> imageDTOList, List<CategoryDTO> categoryDTOList);
    @Mapping(target = "id", source = "game.id")
    @Mapping(target = "name", source = "game.name")
    @Mapping(target = "imgUrl", source = "image")
    @Mapping(target = "listCategory", source = "listCate")
    @Mapping(target = "price", source = "game.price")
    @Mapping(target = "quantity", source = "game.quantity")
    GameCardDTO toDTO(Game game, String image, List<CategoryDTO> listCate);
}
