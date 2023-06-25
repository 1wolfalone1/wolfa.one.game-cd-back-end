//package com.wolfalone.gamecdbackend.model;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//@Slf4j
//public class Oauth2SuccessfulHandler implements AuthenticationSuccessHandler {
//
//    private Logger Logg = LoggerFactory.getLogger(Slf4j.class);
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
////        Logg.info(authentication.getPrincipal().getClass().toString());
////        CustomOauth2User user = (CustomOauth2User) authentication.getPrincipal();
////        request.setAttribute("a", user);
////        request.getRequestDispatcher("/api/v1/authentication/send").forward(request, response);
//    }
//}