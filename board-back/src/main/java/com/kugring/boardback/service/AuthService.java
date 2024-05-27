package com.kugring.boardback.service;

import org.springframework.http.ResponseEntity;

import com.kugring.boardback.dto.request.auth.SignInRequestDto;
import com.kugring.boardback.dto.request.auth.SignUpRequestDto;
import com.kugring.boardback.dto.response.auth.SignInResponseDto;
import com.kugring.boardback.dto.response.auth.SignUpResponseDto;

public interface AuthService {

    // 해당 ? 뒤에 오는 녀석의 모든 부모요소는 받아드리겠다는라는 의미
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
    
}
