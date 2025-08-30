package com.bookstore.system.userservice.services;

import com.bookstore.system.userservice.dtos.LoginRequestDto;
import com.bookstore.system.userservice.dtos.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    public TokenDto sigin(LoginRequestDto login){
        var user = userService.findByEmail(login.email());
        authentication(login);
        return tokenService.createAccessToken(user.getEmail(), List.of(user.getRole().name()));
    }

    private void authentication(LoginRequestDto login){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.email(), login.password()));
    }
}
