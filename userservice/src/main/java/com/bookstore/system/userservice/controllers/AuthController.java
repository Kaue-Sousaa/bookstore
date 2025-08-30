package com.bookstore.system.userservice.controllers;

import com.bookstore.system.userservice.dtos.LoginRequestDto;
import com.bookstore.system.userservice.dtos.TokenDto;
import com.bookstore.system.userservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenDto> sigin(@RequestBody LoginRequestDto login){
        return ResponseEntity.ok(authService.sigin(login));
    }
}
