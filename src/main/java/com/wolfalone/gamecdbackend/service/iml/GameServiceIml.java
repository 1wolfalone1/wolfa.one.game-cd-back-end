package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.config.constant.PagingConstant;
import com.wolfalone.gamecdbackend.dto.*;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.mapper.CategoryMapper;
import com.wolfalone.gamecdbackend.mapper.GameMapper;
import com.wolfalone.gamecdbackend.repository.GameRepo;
import com.wolfalone.gamecdbackend.service.CategoryService;
import com.wolfalone.gamecdbackend.service.GameService;
import com.wolfalone.gamecdbackend.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GameServiceIml implements GameService {


    private final GameMapper gameMapper;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final GameRepo gameRepo;

    @Override
    public ResponseEntity<?> getGameAndPaging(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, PagingConstant.PAGE_NUMBER);
        Page<Game> pageAble = gameRepo.findAll(pageRequest);
//        List<GameCardDTO> gameCardDTOList = gameMapper.toDTO(game)
        pageAble.getTotalPages();
        pageAble.getTotalElements();
        List<GameCardDTO> gameCardDTOList = gameRepo.findAll(pageRequest).getContent().stream().map(game -> {
            GameCardDTO gameCardDTO = gameMapper.toDTO(game, game.getImages().get(game.getImages().size() - 1).getImageUrl(), categoryService.mapListCateToDTO(game.getCategories()));
            return gameCardDTO;
        }).collect(Collectors.toList());
        ListGamePagingDTO list = new ListGamePagingDTO(pageAble.getTotalPages(), pageAble.getTotalElements(), gameCardDTOList);
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<?> getGameDetails(int id) {
        Optional<Game> gameOptional = gameRepo.findById(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            List<ImageDTO> imageDTOList = imageService.mapListImageToListDTO(game.getImages());
            List<CategoryDTO> categoryDTOList = categoryService.mapListCateToDTO(game.getCategories());
            GameDetailsDTO gameDetailsDTO = gameMapper.toDetailsDTO(game, imageDTOList, categoryDTOList);
            return ResponseEntity.ok(gameDetailsDTO);
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Not have any game with " + "id:" + id);
        }
    }


}
