package com.kugring.boardback.controller;

import org.springframework.http.ResponseEntity; // HTTP 응답을 나타내는 클래스
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 인증된 사용자의 정보를 얻기 위한 어노테이션
import org.springframework.web.bind.annotation.PostMapping; // HTTP POST 요청을 처리하기 위한 어노테이션
import org.springframework.web.bind.annotation.RequestBody; // HTTP 요청의 본문을 매개변수로 받기 위한 어노테이션
import org.springframework.web.bind.annotation.RequestMapping; // 특정 URL 경로와 해당 컨트롤러를 매핑하기 위한 어노테이션
import org.springframework.web.bind.annotation.RestController; // RESTful 웹 서비스를 위한 컨트롤러임을 나타내는 어노테이션

import com.kugring.boardback.dto.request.board.PostBoardRequestDto; // 게시글 작성 요청 데이터를 담는 DTO
import com.kugring.boardback.dto.response.board.PostBoardResponseDto; // 게시글 작성 응답 데이터를 담는 DTO
import com.kugring.boardback.service.BoardService; // 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스

import jakarta.validation.Valid; // 유효성 검사를 위한 어노테이션
import lombok.RequiredArgsConstructor; // final 필드에 대해 생성자를 자동으로 생성해주는 Lombok 어노테이션

@RestController // 이 클래스가 RESTful API의 컨트롤러임을 나타냅니다.
@RequestMapping("/api/v1/board") // 이 컨트롤러는 "/api/v1/board" 경로와 매핑됩니다.
@RequiredArgsConstructor // final 필드에 대해 생성자를 자동으로 생성해줍니다.
public class BoardController {
    private final BoardService boardService; // 게시글 관련 비즈니스 로직을 처리하기 위한 서비스 객체입니다.

    @PostMapping("") // 이 메서드는 HTTP POST 요청을 처리합니다.
    
    public ResponseEntity<? super PostBoardResponseDto> postBoard(
        @RequestBody @Valid PostBoardRequestDto requestBody, // 요청 본문을 PostBoardRequestDto 객체로 매핑하고 유효성 검사를 수행합니다.
        @AuthenticationPrincipal String email // 인증된 사용자의 이메일을 매개변수로 받습니다.
    )
     {
        System.out.println("boardcontroller?");
        // boardService의 postBoard 메서드를 호출하여 요청을 처리하고, 그 결과를 response 변수에 저장합니다.
        ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, email);
        // 처리 결과를 클라이언트에게 반환합니다.

        System.out.println("왜 컨트롤러도 실행이 안되는겨...");
        System.out.println(response);
        return response;
    }
    
    

}
