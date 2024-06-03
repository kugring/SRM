package com.kugring.boardback.service;

import org.springframework.http.ResponseEntity;

import com.kugring.boardback.dto.response.user.GetSignInUserResponseDto;

public interface UserService {
    
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);

}
