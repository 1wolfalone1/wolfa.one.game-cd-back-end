package com.wolfalone.gamecdbackend.controller;


import com.wolfalone.gamecdbackend.config.constant.ApiConstant;
import com.wolfalone.gamecdbackend.dto.CreateGameDTO;
import com.wolfalone.gamecdbackend.dto.FilterDataDTO;
import com.wolfalone.gamecdbackend.dto.ListGamePagingDTO;
import com.wolfalone.gamecdbackend.dto.UserInfoDto;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = ApiConstant.FRONT_END_URL, allowCredentials = "true")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("games/{page}")
    public ResponseEntity<?> getGamesPaging(@PathVariable("page") int page) {
        return gameService.getGameAndPaging(page);
    }

    @GetMapping("games/details/{id}")
    public ResponseEntity<?> getGameDetails(@PathVariable("id") int id) {
        return gameService.getGameDetails(id);
    }

    @GetMapping("games/search/{page}")
    public ResponseEntity<?> getGameDetails(@ModelAttribute FilterDataDTO filterData, @PathVariable("page") Integer page) {
        System.out.println(filterData + " - " + page);
        return gameService.filterGame(filterData, page);
    }
    @GetMapping("admin/games/{page}")
    public ResponseEntity<?> getGamesAdmin(@PathVariable("page") Integer page) {
        return gameService.getGameAdminAndPaging(page);
    }

    @PostMapping("admin/games")
    public ResponseEntity<?> createNewGame(@RequestPart("data") CreateGameDTO createGameDTO,
                                           @RequestParam(value = "image", required = false)
                                           List<MultipartFile> images){
        return gameService.createGame(createGameDTO, images);
    }
}
