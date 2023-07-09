package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.config.constant.Constant;
import com.wolfalone.gamecdbackend.config.constant.PagingConstant;
import com.wolfalone.gamecdbackend.dto.*;
import com.wolfalone.gamecdbackend.entity.Category;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.entity.Image;
import com.wolfalone.gamecdbackend.mapper.CategoryMapper;
import com.wolfalone.gamecdbackend.mapper.GameMapper;
import com.wolfalone.gamecdbackend.mapper.ImageMapper;
import com.wolfalone.gamecdbackend.repository.CategoryRepo;
import com.wolfalone.gamecdbackend.repository.GameRepo;
import com.wolfalone.gamecdbackend.repository.ImageRepo;
import com.wolfalone.gamecdbackend.service.CategoryService;
import com.wolfalone.gamecdbackend.service.GameService;
import com.wolfalone.gamecdbackend.service.ImageService;
import com.wolfalone.gamecdbackend.util.S3Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GameServiceIml implements GameService {

    @Autowired
    private Constant constant;
    private final GameMapper gameMapper;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final GameRepo gameRepo;
    private final ImageRepo imageRepo;
    private final CategoryRepo categoryRepo;
    private final ImageMapper imageMapper;
    private final CategoryMapper categoryMapper;
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
            PageRequest pageRequest = PageRequest.of(page - 1,
                    PagingConstant.PAGE_NUMBER,
                    Sort.by(Sort.Direction.DESC, "id"));
            Page<Game> pageAble = gameRepo.findAllByIsDeletedFalse(pageRequest);

            List<GameAdminTableDTO> list = mapListGameEntityToGameAdminTableDTO(
                    gameRepo
                            .findAllByIsDeletedFalse(pageRequest)
                            .getContent()
            );
            PageAdminTableDTO gameAdminTableDTO = PageAdminTableDTO.builder().list(list).totalProduct(pageAble.getTotalElements()).totalPage(pageAble.getTotalPages()).build();
            return ResponseEntity.ok(gameAdminTableDTO);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Some thing went wrong ");
        }
    }

    @Override
    public ResponseEntity<?> createGame(CreateGameDTO createGameDTO, List<MultipartFile> images) throws Exception {
        log.info("images {}", images);
        log.info("ceateGameDTO {}", createGameDTO.toString());
        try {
            List<Category> categories = categoryRepo.findByListId(createGameDTO.getCategory());
            log.info("asdf {}", categories);
// Adjust the auto-increment sequence to start from maxId + 1
            Game game = gameMapper.createGameDTOToGame(createGameDTO, categories);
            game.setIsDeleted(false);

            log.info("game {}", game.toString());
            Game newGame = gameRepo.save(game);
            categories.forEach(category -> {
                category.getGames().add(newGame);
                categoryRepo.save(category);
            });
            saveListImage(images, newGame);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }

    @Override
    public ResponseEntity<?> deleteGame(int id) {
        try {
            Game game = gameRepo.findById(id).get();
            game.setIsDeleted(true);
            gameRepo.save(game);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't delete");
        }
    }

    @Override
    public ResponseEntity<?> getGameForAdminUpdate(int id) {
        try {
            Game gameEntity = gameRepo.findById(id).get();
            List<Image> imagesEntity = imageRepo.findByGameId(id);
            GameForAdminUpdateDTO gameForAdminUpdateDTO = gameMapper.toGameAdminUpdateDTO(gameEntity);
            List<ImageUpdateDTO> imageUpdateDTO = imageMapper.toListImageUpdateDTO(imagesEntity);
            AdminUpdateDTO adminUpdateDTO = AdminUpdateDTO.builder()
                    .images(imageUpdateDTO)
                    .data(gameForAdminUpdateDTO)
                    .build();
            return ResponseEntity.ok(adminUpdateDTO);
        } catch (Exception e) {
            log.info("Error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erorrr");
        }
    }

    @Override
    public ResponseEntity<?> updateGame(List<MultipartFile> images, GameDataUpdateDTO gameDataUpdateDTO) {
        try {
            gameDataUpdateDTO.getOldImages().stream().forEach(image -> {
                log.info("images {}", image);
            });
            Game oldGame = gameRepo.findById(gameDataUpdateDTO.getId()).get();
            List<Category> newCategories = categoryRepo.findAllById(gameDataUpdateDTO.getCategory());
            List<Image> newImages = imageRepo.findAllById(gameDataUpdateDTO.getOldImages());

            Game game = gameMapper.updateDTOToGameEntity(gameDataUpdateDTO, newCategories, newImages, false);
            game.getCategories().clear();
            game.setCategories(newCategories);
            List<Image> oldListImage = oldGame.getImages();
            oldListImage.removeAll(newImages);
            if (images != null) {
                game.getImages().addAll(saveListImageUpdate(images, game));
            } else {
                log.info("image nullllllllllllllll");
            }
            List<Integer> oldIdsImage = oldListImage.stream()
                    .map(image -> image.getId())
                    .collect(Collectors.toList());
            log.info("game {}", game.getCategories());
            log.info("image {}", game.getImages());
            log.info("id {}", game.getId());
            log.info("remove image id {}", oldIdsImage);
            gameRepo.save(game);

            if(oldIdsImage != null && oldIdsImage.size() > 0) {
                imageRepo.deleteAllById(oldIdsImage);
            }
            return ResponseEntity.ok("Ok");
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }


    public void saveListImage(List<MultipartFile> images, Game game) {
        images.stream().forEach(image -> {
            String originUrl = constant.getAws().getS3Url();
            String urlImage = "";
            String contentType = image.getContentType();
            String orther = image.getOriginalFilename();
            InputStream other2 = null;
            try {
                other2 = image.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("Content type {} - {} - {}", contentType, orther, other2);
            String fileName = "game-cd/img/useravatar/" + UUID.randomUUID() + "." +
                    contentType.substring(6);
            urlImage = originUrl + "/" + fileName;
            log.info("Go to s3util ");
            try {
                (new S3Utils()).uploadFile(fileName, other2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageRepo.save(Image.builder().game(game).imageUrl(urlImage).build());
        });
    }
    public List<Image> saveListImageUpdate(List<MultipartFile> images, Game game) {
       return  images.stream().map(image -> {
            String originUrl = constant.getAws().getS3Url();
            String urlImage = "";
            String contentType = image.getContentType();
            String orther = image.getOriginalFilename();
            InputStream other2 = null;
            try {
                other2 = image.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("Content type {} - {} - {}", contentType, orther, other2);
            String fileName = "game-cd/img/useravatar/" + UUID.randomUUID() + "." +
                    contentType.substring(6);
            urlImage = originUrl + "/" + fileName;
            log.info("Go to s3util ");
            try {
                (new S3Utils()).uploadFile(fileName, other2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return imageRepo.save(Image.builder().game(game).imageUrl(urlImage).build());
        }).collect(Collectors.toList());
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
