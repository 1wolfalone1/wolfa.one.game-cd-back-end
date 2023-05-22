package com.wolfalone.gamecdbackend.service;

import com.wolfalone.gamecdbackend.dto.Oauth2Reponse;
import com.wolfalone.gamecdbackend.dto.UserDTO;
import com.wolfalone.gamecdbackend.dto.UserRegistrationDTO;
import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {

    Account getAccountByEmail(String email);
    List<Account> getAllAccount();

    UserDTO authenticate(UserDTO userAuthentication) throws Exception;

    Optional<UserDTO> registerNewAccount(UserRegistrationDTO payload);

    Optional<UserDTO> verifyEmail(String email, String code);

    Account registerNewAccountByOauth2(Oauth2Reponse response);
}
