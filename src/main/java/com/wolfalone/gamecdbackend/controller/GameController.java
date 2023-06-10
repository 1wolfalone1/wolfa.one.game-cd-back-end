package com.wolfalone.gamecdbackend.controller;


import com.wolfalone.gamecdbackend.config.constant.ApiConstant;
import com.wolfalone.gamecdbackend.dto.FilterDataDTO;
import com.wolfalone.gamecdbackend.dto.ListGamePagingDTO;
import com.wolfalone.gamecdbackend.entity.Game;
import com.wolfalone.gamecdbackend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@CrossOrigin(origins = ApiConstant.FRONT_END_URL, allowCredentials = "true")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/{page}")
    public ResponseEntity<?> getGamesPaging(@PathVariable("page") int page) {
        return gameService.getGameAndPaging(page);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getGameDetails(@PathVariable("id") int id) {
        return gameService.getGameDetails(id);
    }

    @GetMapping("/search/{page}")
    public ResponseEntity<?> getGameDetails(@ModelAttribute FilterDataDTO filterData, @PathVariable("page") Integer page) {
        System.out.println(filterData + " - " + page);
        return gameService.filterGame(filterData, page);
    }
}
