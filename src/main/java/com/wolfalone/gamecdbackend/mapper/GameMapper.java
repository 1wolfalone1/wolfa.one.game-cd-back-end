package com.wolfalone.gamecdbackend.mapper;

import com.wolfalone.gamecdbackend.dto.*;
import com.wolfalone.gamecdbackend.entity.Category;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface GameMapper {

    @Mapping(target = "id", source = "game.id")
    @Mapping(target = "name", source = "game.name")
    @Mapping(target = "imageList", source = "imageDTOList")
    @Mapping(target = "categoryList", source = "categoryDTOList")
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

    @Mapping(target = "id", source = "game.id")
    @Mapping(target = "name", source = "game.name")
    @Mapping(target = "price", source = "game.price")
    @Mapping(target = "quantity", source = "game.quantity")
    GameAdminTableDTO toAdminTableDTO(Game game);

    @Mapping(target = "name", source = "gameDTO.name")
    @Mapping(target = "description", source = "gameDTO.description")
    @Mapping(target = "price", source = "gameDTO.price")
    @Mapping(target = "quantity", source = "gameDTO.quantity")
    @Mapping(target = "categories", source = "categories")
    Game createGameDTOToGame(CreateGameDTO gameDTO, List<Category> categories);

    List<Category> listIntToCategory(List<Integer> category);

    @Mapping(target = "id", source = "id")
    Category intToCategory(Integer id);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "category", source = "categories")
    GameForAdminUpdateDTO toGameAdminUpdateDTO(Game gameEntity);

    @Mapping(target = "id", source = "gameDTO.id")
    @Mapping(target = "name", source = "gameDTO.name")
    @Mapping(target = "description", source = "gameDTO.description")
    @Mapping(target = "price", source = "gameDTO.price")
    @Mapping(target = "quantity", source = "gameDTO.quantity")
    @Mapping(target = "images", source = "newImages")
    @Mapping(target = "categories", source = "newCategories")
    @Mapping(target = "isDeleted", source = "isDeleted")
    Game updateDTOToGameEntity(GameDataUpdateDTO gameDTO
            , List<Category> newCategories
            , List<Image> newImages, Boolean isDeleted);
}
