package com.kugring.boardback.dto.response.board;

import com.kugring.boardback.common.ResponseCode;
import com.kugring.boardback.common.ResponseMessage;
import com.kugring.boardback.dto.response.ResponseDto;
import com.kugring.boardback.entity.ImageEntity;
import com.kugring.boardback.repository.resultSet.GetBoardResultSet;

import lombok.Getter;
import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Getter
public class GetBoardResponseDto extends ResponseDto {
    
    private int boardNumber;
    private String title;
    private String content;
    private List<String> boardImageList;
    private String writeDatetime;
    private String writerEmail;
    private String writerNickname;
    private String writerProfileImage;

    private GetBoardResponseDto(GetBoardResultSet resultSet, List<ImageEntity> imageEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);


        // 해당 이미지는 보드엔터티에서 가져오는 것이 아니라
        // 이미지엔터티에서 데이터형식을 가져와 boardImageList에 이미지들을 추가한다.
        List<String> boardImageList = new ArrayList<>();
        for (ImageEntity imageEntity: imageEntities) {
            String boardImage = imageEntity.getImage();
            boardImageList.add(boardImage);
        }


        this.boardNumber = resultSet.getBoardNumber();
        this.title = resultSet.getTitle();
        this.content = resultSet.getContent();
        this.boardImageList = boardImageList;
        this.writeDatetime = resultSet.getWriteDatetime();
        this.writerEmail = resultSet.getWriterEmail();
        this.writerNickname = resultSet.getWriterNinckname();
        this.writerProfileImage = resultSet.getWriterProfileImage();
    }

    public static ResponseEntity<GetBoardResponseDto> success(GetBoardResultSet resultSet, List<ImageEntity> imageEntities){
        GetBoardResponseDto result = new GetBoardResponseDto(resultSet, imageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistBoard() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
