package com.kugring.boardback.service;

import org.springframework.http.ResponseEntity;

import com.kugring.boardback.dto.request.board.PostBoardRequestDto;
import com.kugring.boardback.dto.response.board.PostBoardResponseDto;

public interface BoardService {
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
    
}
