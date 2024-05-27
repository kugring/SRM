package com.kugring.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kugring.boardback.dto.request.auth.SignInRequestDto;
import com.kugring.boardback.dto.request.auth.SignUpRequestDto;
import com.kugring.boardback.dto.response.ResponseDto;
import com.kugring.boardback.dto.response.auth.SignInResponseDto;
import com.kugring.boardback.dto.response.auth.SignUpResponseDto;
import com.kugring.boardback.entity.UserEntity;
import com.kugring.boardback.provider.JwtProvider;
import com.kugring.boardback.repository.UserRepository;
import com.kugring.boardback.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    // 암호학을 위해서 final사용하지 않고 직접 내부에서 의존성을 주입할것이다.
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail)
                return SignUpResponseDto.duplicateEmail();

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname)
                return SignUpResponseDto.duplicateNickname();

            String telNumber = dto.getTelNumber();
            boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
            if (existedTelNumber)
                return SignUpResponseDto.duplicateTelNumber();

            // 패스워드를 암호화
            String password = dto.getPassword();
            System.out.println("Raw Password: " + password);
            String encodedPassword = passwordEncoder.encode(password);
            System.out.println("Encoded Password during sign-up: " + encodedPassword);

            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String token = null;

        try {
            String email = dto.getEmail();
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return SignInResponseDto.signInFail();

            // 비밀번호 확인
            String password = dto.getPassword();
            System.out.println("Input Password: " + password);
            String encodedPassword = userEntity.getPassword();
            System.out.println("Encoded Password: " + encodedPassword);

            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            System.out.println("Password matched: " + isMatched);

            if (!isMatched) {
                System.out.println("Password mismatch.");
                return SignInResponseDto.signInFail();
            }

            // JWT 토큰 생성
            token = jwtProvider.create(email);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);
    }

}
