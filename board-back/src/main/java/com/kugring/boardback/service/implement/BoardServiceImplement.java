package com.kugring.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kugring.boardback.dto.request.board.PostBoardRequestDto;
import com.kugring.boardback.dto.response.ResponseDto;
import com.kugring.boardback.dto.response.board.PostBoardResponseDto;
import com.kugring.boardback.entity.BoardEntity;
import com.kugring.boardback.entity.ImageEntity;
import com.kugring.boardback.repository.BoardRePository;
import com.kugring.boardback.repository.ImageRepository;
import com.kugring.boardback.repository.UserRepository;
import com.kugring.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {
    
    private final UserRepository userRepository;
    private final BoardRePository boardRePository;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {

        System.out.println("Try boardService");
        try {

            // 존재하는 유저인지 확인
            boolean existedEmail = userRepository.existsByEmail(email);
            
            System.out.println("User Return:"+existedEmail);
            // 존재하지 않는경우에는 존재하지 않는다로 반환
            if (!existedEmail) return PostBoardResponseDto.notExistUser();
            
            // 보드엔터티에서 빌더를 적용해서 빌더를 쓰는 사람들도 많지만 본인 취향이 아니닷!
            
            System.out.println("board entity befor");
            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRePository.save(boardEntity);
            
            int boardNumber = boardEntity.getBoardNumber();

            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            System.out.println("img for befor");
            for(String image: boardImageList){
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }
            
            System.out.println("image save all");
            // 원래는 for문안에 save를 넣어서 하나씩 저장을 해도 되지만 그렇게 되어지만 한번에 db가 많은 양을 저장하기 때문에 한번에 몰아서 (saveAll)저장해주는게 좋다!
            imageRepository.saveAll(imageEntities);
            

        } catch (Exception exception) {
            
            System.out.println("boar service impliment catch");
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        System.out.println("board service success dto");
        return PostBoardResponseDto.success();
    }

}
