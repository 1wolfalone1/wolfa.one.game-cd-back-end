package com.wolfalone.gamecdbackend.service.iml;

import com.wolfalone.gamecdbackend.dto.Oauth2Reponse;
import com.wolfalone.gamecdbackend.dto.UserDTO;
import com.wolfalone.gamecdbackend.dto.UserRegistrationDTO;
import com.wolfalone.gamecdbackend.entity.Account;
import com.wolfalone.gamecdbackend.entity.Users;
import com.wolfalone.gamecdbackend.event.EventHandle;
import com.wolfalone.gamecdbackend.event.SendEmailEvent;
import com.wolfalone.gamecdbackend.mapper.AccountMapper;
import com.wolfalone.gamecdbackend.mapper.UserMapper;
import com.wolfalone.gamecdbackend.repository.AccountRepo;
import com.wolfalone.gamecdbackend.repository.UserRepo;
import com.wolfalone.gamecdbackend.service.AccountService;
import com.wolfalone.gamecdbackend.service.EmailService;
import com.wolfalone.gamecdbackend.util.MyRamdom;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.UUID;


@Service
@Transactional
public class AccountServiceIml implements AccountService {

    @Autowired
    private EventHandle eventHandle;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AccountRepo accountRepo;


    @Autowired
    private ApplicationEventPublisher publisher;
    @Override
    public Account getAccountByEmail(String email) {
        Optional<Account> acc = accountRepo.findByEmail(email);
        return acc.orElse(null);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepo.findAll();
    }

    @Override
    public UserDTO authenticate(UserDTO userAuthentication) throws Exception {
        String email = userAuthentication.email();
        String password = userAuthentication.token();
        if (password != null) {

            Account acc = accountRepo.findByEmailAndPassword(email, password);

            if (acc != null) {
                String token = jwtService.generateToken(acc);
                acc.setTokenId(token);
            } else {
                return null;
            }

            return userMapper.toDTO(acc.getUser(), acc);

        } else {
            return null;
        }

    }

    @Override
    public Optional<UserDTO> registerNewAccount(UserRegistrationDTO payload) {
        long count = accountRepo.countByEmailAndActive(payload.email());
        Account account = accountRepo.countByEmailAndPending(payload.email());
        if (count > 0) {
            System.out.println("newAccount");
            return Optional.empty();
        } else if (account != null) {
            Account newAccount = accountMapper.toEntity(payload);
            newAccount.setId(account.getId());
            String randomVerifyCode = MyRamdom.generateString(11);
            newAccount.setVerifiedEmailCode(randomVerifyCode);
            newAccount.setRole(0);
            newAccount.setStatus(0);
            System.out.println(newAccount);
            Users user = userRepo.save(newAccount.getUser());
            newAccount.getUser().setId(user.getId());

            eventHandle.sendEmail(newAccount.getEmail(), randomVerifyCode, "veirradfas");

            accountRepo.save(newAccount);
            UserDTO accDTO = userMapper.toDTO(newAccount.getUser(), newAccount);
            return Optional.of(accDTO);
        } else {
            Account newAccount = accountMapper.toEntity(payload);
            String randomVerifyCode = MyRamdom.generateString(11);
            newAccount.setVerifiedEmailCode(randomVerifyCode);
            newAccount.setRole(0);
            newAccount.setStatus(0);
            System.out.println(newAccount);
            Users user = userRepo.save(newAccount.getUser());
            newAccount.getUser().setId(user.getId());
            eventHandle.sendEmail(newAccount.getEmail(), randomVerifyCode, "veirradfas");

            accountRepo.save(newAccount);
            UserDTO accDTO = userMapper.toDTO(newAccount.getUser(), newAccount);
            return Optional.of(accDTO);
        }
    }

    @Override
    public Optional<UserDTO> verifyEmail(String email, String code) {
        Account acc = accountRepo.findByEmailAndVerifiedEmailCodeAndStatus(email, code, 0);
        if (acc != null) {
            acc.setStatus(1);
            String token = jwtService.generateToken(acc);
            acc.setTokenId(token);
            accountRepo.save(acc);
            UserDTO user = userMapper.toDTO(acc.getUser(), acc);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Account registerNewAccountByOauth2(Oauth2Reponse response) {
        Account acc = accountMapper.oauth2UserToEntity(response);
        acc.setRole(0);
        acc.setStatus(1);
        Users user = userRepo.save(acc.getUser());
        acc.setUser(user);
        return accountRepo.save(acc);
    }
}


