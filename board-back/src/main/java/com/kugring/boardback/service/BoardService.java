package com.kugring.boardback.service;

import org.springframework.http.ResponseEntity;

import com.kugring.boardback.dto.response.board.GetBoardResponseDto;
import com.kugring.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.kugring.boardback.dto.response.board.GetCommentListResponseDto;
import com.kugring.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.kugring.boardback.dto.response.board.GetTop3BoardListResponseDto;
import com.kugring.boardback.dto.response.board.PostBoardResponseDto;
import com.kugring.boardback.dto.response.board.GetSearchBoardListResponseDto;
import com.kugring.boardback.dto.response.board.GetUserBoardListResponseDto;
import com.kugring.boardback.dto.response.board.PostCommentResponseDto;
import com.kugring.boardback.dto.response.board.PutFavoriteResponseDto;
import com.kugring.boardback.dto.response.board.PatchBoardResponseDto;
import com.kugring.boardback.dto.response.board.IncreaseViewCountResponseDto;
import com.kugring.boardback.dto.response.board.DeleteBoardResponseDto;
import com.kugring.boardback.dto.request.board.PatchBoardRequestDto;
import com.kugring.boardback.dto.request.board.PostBoardRequestDto;
import com.kugring.boardback.dto.request.board.PostCommentRequestDto;

public interface BoardService {
    // path 벨리어블로 보드 넘버를 넣어줄 예정이다.
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);
    ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList();
    ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList();
    ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord);
    ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email);
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);
    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);
    ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email);
    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);
    ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email);
}
