package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.config.constant.PagingConstant;
import com.wolfalone.gamecdbackend.dto.*;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.mapper.GameMapper;
import com.wolfalone.gamecdbackend.repository.GameRepo;
import com.wolfalone.gamecdbackend.service.CategoryService;
import com.wolfalone.gamecdbackend.service.GameService;
import com.wolfalone.gamecdbackend.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
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
        List<GameCardDTO> gameCardDTOList = mapListGameEntityToGameDTO(gameRepo.findAll(pageRequest).getContent());

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not have any game with " + "id:" + id);
        }
    }

    @Override
    public ResponseEntity<?> filterGame(FilterDataDTO filterDataDTO, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, PagingConstant.PAGE_NUMBER);
        Integer fromPrice = filterDataDTO.from();
        Integer toPrice = filterDataDTO.to();
        Integer categotyId = filterDataDTO.category();
        int hasCategoryId = 1;
        if (categotyId == 0) {
            hasCategoryId = 0;
        }
        if (toPrice == null) {
            toPrice = Integer.MAX_VALUE;
        }
        if (fromPrice == null) {
            fromPrice = 0;
        }
        Page<Game> pageAble = gameRepo.filterByNameAndPriceRange(filterDataDTO.name(), fromPrice, toPrice, hasCategoryId, categotyId, pageRequest);
        List<Game> games = pageAble.getContent();

        int totalPage = pageAble.getTotalPages();
        long totalElement = pageAble.getTotalElements();
        System.out.println(games + "--[]--");
        System.out.println(totalPage + "--[]--");
        System.out.println(totalElement + "--[]--");
        List<GameCardDTO> gameCardDTOList = mapListGameEntityToGameDTO(games);
        ListGamePagingDTO listGamePagingDTO = new ListGamePagingDTO(totalPage, totalElement, gameCardDTOList);
        return ResponseEntity.ok(listGamePagingDTO);
    }

    @Override
    public ResponseEntity<?> getGameAdminAndPaging(Integer page) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, PagingConstant.PAGE_NUMBER);
            Page<Game> pageAble = gameRepo.findAll(pageRequest);
            List<GameAdminTableDTO> list = mapListGameEntityToGameAdminTableDTO(gameRepo.findAll(pageRequest).getContent());
            PageAdminTableDTO gameAdminTableDTO = PageAdminTableDTO.builder().list(list).totalProduct(pageAble.getTotalElements()).totalPage(pageAble.getTotalPages()).build();
            return ResponseEntity.ok(gameAdminTableDTO);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Some thing went wrong ");
        }
    }

    @Override
    public ResponseEntity<?> createGame(CreateGameDTO createGameDTO, List<MultipartFile> images) {
        log.info("images {}", images);
        log.info("ceateGameDTO {}", createGameDTO.toString());
        return null;
    }

    public List<GameCardDTO> mapListGameEntityToGameDTO(List<Game> games) {
        return games.stream().map(game -> {
            GameCardDTO gameCardDTO = gameMapper.toDTO(game, game.getImages().get(game.getImages().size() - 1).getImageUrl(), categoryService.mapListCateToDTO(game.getCategories()));
            return gameCardDTO;
        }).collect(Collectors.toList());
    }

    public List<GameAdminTableDTO> mapListGameEntityToGameAdminTableDTO(List<Game> games) {
        return games.stream().map(game -> {
            return gameMapper.toAdminTableDTO(game);
        }).collect(Collectors.toList());
    }
}
