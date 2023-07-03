package com.wolfalone.gamecdbackend.service;

import com.wolfalone.gamecdbackend.dto.ChangeUserProfileDTO;
import com.wolfalone.gamecdbackend.dto.UserInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserService {

    ResponseEntity<?> changUserProfile(UserInfoDto changeUserProfileDTO, MultipartFile image) throws IOException;
}
