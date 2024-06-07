package com.kugring.boardback.service;

import org.springframework.http.ResponseEntity;

import com.kugring.boardback.dto.request.board.PostBoardRequestDto;
import com.kugring.boardback.dto.request.board.PostCommentRequestDto;
import com.kugring.boardback.dto.response.board.GetBoardResponseDto;
import com.kugring.boardback.dto.response.board.GetCommentListResponseDto;
import com.kugring.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.kugring.boardback.dto.response.board.PostBoardResponseDto;
import com.kugring.boardback.dto.response.board.PutFavoriteResponseDto;
import com.kugring.boardback.dto.response.board.PostCommentResponseDto;

public interface BoardService {
    // path 벨리어블로 보드 넘버를 넣어줄 예정이다.
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);
    
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);

    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);
    
}
