package com.kugring.boardback.dto.response.board;

import com.kugring.boardback.common.ResponseCode;
import com.kugring.boardback.common.ResponseMessage;
import com.kugring.boardback.dto.object.BoardListItem;
import com.kugring.boardback.dto.response.ResponseDto;
import com.kugring.boardback.entity.BoardListViewEntity;

import lombok.Getter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetSearchBoardListResponseDto extends ResponseDto {
    private List<BoardListItem> searchList;

    private GetSearchBoardListResponseDto(List<BoardListViewEntity> boardListViewEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.searchList = BoardListItem.getList(boardListViewEntities);
    }

    public static ResponseEntity<GetSearchBoardListResponseDto> success(List<BoardListViewEntity> boardListViewEntities){
        GetSearchBoardListResponseDto result = new GetSearchBoardListResponseDto(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
