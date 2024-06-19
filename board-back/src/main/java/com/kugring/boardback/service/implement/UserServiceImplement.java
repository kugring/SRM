package com.kugring.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kugring.boardback.dto.request.user.PatchNicknameRequestDto;
import com.kugring.boardback.dto.request.user.PatchProfileImageRequestDto;
import com.kugring.boardback.dto.response.ResponseDto;
import com.kugring.boardback.dto.response.user.GetSignInUserResponseDto;
import com.kugring.boardback.dto.response.user.GetUserResponseDto;
import com.kugring.boardback.dto.response.user.PatchNicknameResponseDto;
import com.kugring.boardback.dto.response.user.PatchProfileImageResponseDto;
import com.kugring.boardback.entity.UserEntity;
import com.kugring.boardback.repository.UserRepository;
import com.kugring.boardback.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;


    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {

        UserEntity userEntity = null;

        try {

            userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return GetUserResponseDto.noExistUser();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserResponseDto.success(userEntity);
    }  
    
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

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {
        

        try {
            
            UserEntity userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return PatchNicknameResponseDto.noExistUser();

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if(existedNickname) return PatchNicknameResponseDto.duplicateNickname();

            userEntity.setNickname(nickname);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchNicknameResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email) {
        

        try {

            UserEntity userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return PatchProfileImageResponseDto.noExistUser();

            String proFileImage = dto.getProfileImage();
            userEntity.setProfileImage(proFileImage);
            userRepository.save(userEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchProfileImageResponseDto.success();
    }

}
