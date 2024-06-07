package com.kugring.boardback.controller;

import org.springframework.http.ResponseEntity; // HTTP 응답을 나타내는 클래스
import org.springframework.security.core.annotation.AuthenticationPrincipal; // 인증된 사용자의 정보를 얻기 위한 어노테이션
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // HTTP POST 요청을 처리하기 위한 어노테이션
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; // HTTP 요청의 본문을 매개변수로 받기 위한 어노테이션
import org.springframework.web.bind.annotation.RequestMapping; // 특정 URL 경로와 해당 컨트롤러를 매핑하기 위한 어노테이션
import org.springframework.web.bind.annotation.RestController; // RESTful 웹 서비스를 위한 컨트롤러임을 나타내는 어노테이션

import com.kugring.boardback.dto.request.board.PostBoardRequestDto; // 게시글 작성 요청 데이터를 담는 DTO
import com.kugring.boardback.dto.request.board.PostCommentRequestDto;
import com.kugring.boardback.dto.response.board.PostCommentResponseDto;
import com.kugring.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.kugring.boardback.dto.response.board.GetCommentListResponseDto;
import com.kugring.boardback.dto.response.board.PostBoardResponseDto; // 게시글 작성 응답 데이터를 담는 DTO
import com.kugring.boardback.dto.response.board.GetBoardResponseDto;
import com.kugring.boardback.dto.response.board.PutFavoriteResponseDto;
import com.kugring.boardback.service.BoardService; // 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스

import jakarta.validation.Valid; // 유효성 검사를 위한 어노테이션
import lombok.RequiredArgsConstructor; // final 필드에 대해 생성자를 자동으로 생성해주는 Lombok 어노테이션

@RestController // 이 클래스가 RESTful API의 컨트롤러임을 나타냅니다.
@RequestMapping("/api/v1/board") // 이 컨트롤러는 "/api/v1/board" 경로와 매핑됩니다.
@RequiredArgsConstructor // final 필드에 대해 생성자를 자동으로 생성해줍니다.
public class BoardController {
    private final BoardService boardService; // 게시글 관련 비즈니스 로직을 처리하기 위한 서비스 객체입니다.

    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
        @PathVariable("boardNumber") Integer boardNumber
    ){
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);
        return response;
    }
    
    @GetMapping("/{boardNumber}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
        @PathVariable("boardNumber") Integer boardNumber
    ){
        ResponseEntity<? super GetFavoriteListResponseDto> response = boardService.getFavoriteList(boardNumber);
        return response;
    }

    @GetMapping("/{boardNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(
        @PathVariable("boardNumber") Integer boardNumber
    ){
        ResponseEntity<? super GetCommentListResponseDto> response =boardService.getCommentList(boardNumber);
        return response;
    }

    @PostMapping("") // 이 메서드는 HTTP POST 요청을 처리합니다.
    public ResponseEntity<? super PostBoardResponseDto> postBoard(
        @RequestBody @Valid PostBoardRequestDto requestBody, // 요청 본문을 PostBoardRequestDto 객체로 매핑하고 유효성 검사를 수행합니다.
        @AuthenticationPrincipal String email // 인증된 사용자의 이메일을 매개변수로 받습니다.
    )
     {
        // boardService의 postBoard 메서드를 호출하여 요청을 처리하고, 그 결과를 response 변수에 저장합니다.
        ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, email);
        // 처리 결과를 클라이언트에게 반환합니다.
        System.out.println(response);
        return response;
    }

    @PostMapping("/{boardNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postComment(
        @RequestBody @Valid PostCommentRequestDto requestBody,
        @PathVariable("boardNumber") Integer boardNumber,
        @AuthenticationPrincipal String email
    ){
        ResponseEntity<? super PostCommentResponseDto> response = boardService.postComment(requestBody, boardNumber, email);
        return response;
    }
    
    // 해당 보드 넘버와 이메일을 넘긴다.
    @PutMapping("/{boardNumber}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
        @PathVariable("boardNumber") Integer boardNumber,
        @AuthenticationPrincipal String email
    ) {
        ResponseEntity<? super PutFavoriteResponseDto> response = boardService.putFavorite(boardNumber, email);
        return response;
    }
    

}
