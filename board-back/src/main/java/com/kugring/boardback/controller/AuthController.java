package com.kugring.boardback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kugring.boardback.dto.request.auth.SignInRequestDto;
import com.kugring.boardback.dto.request.auth.SignUpRequestDto;
import com.kugring.boardback.dto.response.auth.SignInResponseDto;
import com.kugring.boardback.dto.response.auth.SignUpResponseDto;
import com.kugring.boardback.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 컨트롤러에는 비지니스 로직이 적히면 안된다? 컨트롤러는 검증처리는 하는 구역이다. 비지니스 로직은 가능한 서비스 레이웃구간에서 처리되어야 한다.
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
            @RequestBody @Valid SignUpRequestDto requestBody
    ) {
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
        @RequestBody @Valid SignInRequestDto requestBody
    ){
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }

}
