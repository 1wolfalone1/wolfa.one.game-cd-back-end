package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.config.constant.Constant;
import com.wolfalone.gamecdbackend.dto.ChangeUserProfileDTO;
import com.wolfalone.gamecdbackend.dto.ErrorResponse;
import com.wolfalone.gamecdbackend.dto.UserDTO;
import com.wolfalone.gamecdbackend.dto.UserInfoDto;
import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.entity.Users;
import com.wolfalone.gamecdbackend.repository.AccountRepo;
import com.wolfalone.gamecdbackend.repository.UserRepo;
import com.wolfalone.gamecdbackend.service.UserService;
import com.wolfalone.gamecdbackend.util.S3Utils;
import jakarta.mail.Multipart;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserServiceIml implements UserService {

    @Autowired
    private Constant constant;

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;
    @Override
    public ResponseEntity<?> changUserProfile(UserInfoDto changeUserProfileDTO,
                                              MultipartFile image) throws IOException {
        String originUrl = constant.getAws().getS3Url();
        String urlImage = "";

        if(image != null && !image.isEmpty()) {
            String contentType = image.getContentType();
            String orther = image.getOriginalFilename();
            String other2 = image.getInputStream().toString();
            log.info("Content type {} - {} - {}", contentType, orther, other2);
            String fileName = "game-cd/img/useravatar/"  +  UUID.randomUUID() + "." +
                    contentType.substring(6);
            urlImage = originUrl + "/"+  fileName;
            try {
                log.info("Go to s3util ");
                (new S3Utils()).uploadFile(fileName, image.getInputStream());
            } catch (Exception e) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .errorMessage("Error s3 upload image")
                        .build();
                log.info("e {}", e.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        }
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("email of user that upload image {}", email);
        Account account = accountRepo.findByEmail(email).get();
        Users user = account.getUser();
        user.setPhone(changeUserProfileDTO.phone());
        user.setAddress(changeUserProfileDTO.address());
        user.setFullName(changeUserProfileDTO.name());
        account.setAvatarPath(urlImage);
        Users newUser = userRepo.save(user);
        account.setUser(newUser);
        Account newAccount = accountRepo.save(account);
        log.info("new Account {}", newAccount);
        String token = jwtService.generateToken(account);
        UserDTO userDTO  = UserDTO.builder()
                .email(account.getEmail())
                .name(user.getFullName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .image(account.getAvatarPath())
                .id(account.getId())
                .token(token)
                .build();
        return ResponseEntity.ok().body(userDTO);
    }
}
