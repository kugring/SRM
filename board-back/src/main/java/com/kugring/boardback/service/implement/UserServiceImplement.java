package com.kugring.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kugring.boardback.dto.response.ResponseDto;
import com.kugring.boardback.dto.response.user.GetSignInUserResponseDto;
import com.kugring.boardback.entity.UserEntity;
import com.kugring.boardback.repository.UserRepository;
import com.kugring.boardback.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {

        UserEntity userEntity = null;

        System.out.println("user service impl");
        try{

            userEntity = userRepository.findByEmail(email);
            System.out.println("user service entity if befor");
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();

        } catch (Exception exception) {
            System.out.println("user service catch");
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    
        return GetSignInUserResponseDto.success(userEntity);
    }

}
