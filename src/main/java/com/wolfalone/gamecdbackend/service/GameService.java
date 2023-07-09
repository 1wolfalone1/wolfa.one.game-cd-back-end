package com.wolfalone.gamecdbackend.service;

import com.wolfalone.gamecdbackend.dto.*;
import com.wolfalone.gamecdbackend.entity.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface GameService {
    ResponseEntity<?> getGameAndPaging(int page);
    ResponseEntity<?> getGameDetails(int id);
    ResponseEntity<?> filterGame(FilterDataDTO filterDataDTO , int page);

    ResponseEntity<?> getGameAdminAndPaging(Integer page);

    ResponseEntity<?> createGame(CreateGameDTO createGameDTO, List<MultipartFile> images) throws Exception;

    ResponseEntity<?> deleteGame(int id);

    ResponseEntity<?> getGameForAdminUpdate(int id);

    ResponseEntity<?> updateGame(List<MultipartFile> images, GameDataUpdateDTO gameDataUpdateDTO);
}
