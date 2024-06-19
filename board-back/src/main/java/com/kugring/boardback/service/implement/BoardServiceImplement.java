package com.kugring.boardback.service.implement;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kugring.boardback.dto.request.board.PatchBoardRequestDto;
import com.kugring.boardback.dto.request.board.PostBoardRequestDto;
import com.kugring.boardback.dto.request.board.PostCommentRequestDto;
import com.kugring.boardback.dto.response.ResponseDto;
import com.kugring.boardback.dto.response.board.DeleteBoardResponseDto;
import com.kugring.boardback.dto.response.board.GetBoardResponseDto;
import com.kugring.boardback.dto.response.board.GetCommentListResponseDto;
import com.kugring.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.kugring.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.kugring.boardback.dto.response.board.GetSearchBoardListResponseDto;
import com.kugring.boardback.dto.response.board.GetTop3BoardListResponseDto;
import com.kugring.boardback.dto.response.board.GetUserBoardListResponseDto;
import com.kugring.boardback.dto.response.board.IncreaseViewCountResponseDto;
import com.kugring.boardback.dto.response.board.PatchBoardResponseDto;
import com.kugring.boardback.dto.response.board.PostBoardResponseDto;
import com.kugring.boardback.dto.response.board.PostCommentResponseDto;
import com.kugring.boardback.dto.response.board.PutFavoriteResponseDto;
import com.kugring.boardback.entity.BoardEntity;
import com.kugring.boardback.entity.BoardListViewEntity;
import com.kugring.boardback.entity.CommentEntity;
import com.kugring.boardback.entity.FavoriteEntity;
import com.kugring.boardback.entity.ImageEntity;
import com.kugring.boardback.entity.SearchLogEntity;
import com.kugring.boardback.repository.BoardListViewRepository;
import com.kugring.boardback.repository.BoardRepository;
import com.kugring.boardback.repository.CommentRePository;
import com.kugring.boardback.repository.FavoriteRepository;
import com.kugring.boardback.repository.ImageRepository;
import com.kugring.boardback.repository.SearchLogRepository;
import com.kugring.boardback.repository.UserRepository;
import com.kugring.boardback.repository.resultSet.GetBoardResultSet;
import com.kugring.boardback.repository.resultSet.GetCommentListResultSet;
import com.kugring.boardback.repository.resultSet.GetFavoriteListResultSet;
import com.kugring.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    // 받아온 데이터를 세이브 하기 위해서 레포지토리 불러온다?
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final CommentRePository commentRePository;
    private final FavoriteRepository favoriteRepository;
    private final SearchLogRepository searchLogRepository;
    private final BoardListViewRepository boardListViewRepository; 

    // 보드서비스에서 오버라이드를 해오고 트라이캣취로 쓰레드를 잡아준 다음 트라이에 성공했을 경우 겟보드리스폰Dto에서 성공 상태을 실행한다.
    // 임플리먼트에서 실패시는 데이터베이스의 오류를 반환한다.
    // 성공값으로는 해당
    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        // BoardRepository에서 데이터를 꺼내올것이다!
        // 일반적으로 보드테이블의 컬럼으로 없는 닉네임값과 프로필 사진 등등을 못가져오는데 해당 테이블을 조인해서 가져올것이다.
        // 그런데 그 과정에서 조인된 테이블은 쉽게 쿼리문으로는 가져올 수 없는 모양이다.
        // 그래서 쿼리문 말고 네이티브 쿼리를 사용해서 실제 sql을 작성해줄것이다.
        // jpa에서 사용할 수 있는 jpql이랑 sql이 있는데 그중에 sql을 사용할것이다.

        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            resultSet = boardRepository.getBoard(boardNumber);
            if (resultSet == null)
                return GetBoardResponseDto.notExistBoard();

            imageEntities = imageRepository.findByBoardNumber(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetBoardResponseDto.success(resultSet, imageEntities);
    }

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {

        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard)
                return GetFavoriteListResponseDto.noExistBoard();

            resultSets = favoriteRepository.getFavoriteList(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetFavoriteListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {

        List<GetCommentListResultSet> resultSets = new ArrayList<>();

        try {

            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard)
                return GetCommentListResponseDto.noExistBoard();

            resultSets = commentRePository.getCommentList(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetCommentListResponseDto.success(resultSets);

    }

    
    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {

        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try {

            boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetLatestBoardListResponseDto.success(boardListViewEntities);
    }

    
    @Override
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {

        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try {
            //일주일 동안의 날짜
            Date beforeWeek = Date.from(Instant.now().minus(7,ChronoUnit.DAYS));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sevenDaysAgo = simpleDateFormat.format(beforeWeek);

            boardListViewEntities = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetTop3BoardListResponseDto.success(boardListViewEntities);
    }


    @Override
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord) {


        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try {
            
            boardListViewEntities = boardListViewRepository.findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(searchWord, searchWord);

            SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
            searchLogRepository.save(searchLogEntity);
            boolean relation = preSearchWord != null;
            if(relation){
                searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, relation);
                searchLogRepository.save(searchLogEntity);
            }

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSearchBoardListResponseDto.success(boardListViewEntities);
    }

    
    @Override
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email) {

        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try {

            boolean existedUser = userRepository.existsByEmail(email);
            if(!existedUser) return GetUserBoardListResponseDto.noExisUser();

            boardListViewEntities = boardListViewRepository.findByWriterEmailOrderByWriteDatetimeDesc(email);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserBoardListResponseDto.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {

        try {

            // 존재하는 유저인지 확인
            boolean existedEmail = userRepository.existsByEmail(email);

            // 존재하지 않는경우에는 존재하지 않는다로 반환
            if (!existedEmail)
                return PostBoardResponseDto.noExistUser();

            // 보드엔터티에서 빌더를 적용해서 빌더를 쓰는 사람들도 많지만 본인 취향이 아니닷!

            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            int boardNumber = boardEntity.getBoardNumber();

            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }

            // 원래는 for문안에 save를 넣어서 하나씩 저장을 해도 되지만 그렇게 되어지만 한번에 db가 많은 양을 저장하기 때문에 한번에 몰아서
            // (saveAll)저장해주는게 좋다!
            imageRepository.saveAll(imageEntities);

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber,
            String email) {

        try {

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return PostCommentResponseDto.noExistBoard();

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser)
                return PostCommentResponseDto.noExistUser();

            CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
            commentRePository.save(commentEntity);

            boardEntity.increaseCommentCount();
            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostCommentResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {

        try {

            // jwt.io에서 디버거에서 jwt를 만들어서 접속할 수도 있는데 favorite테이블은 제약조건이 있는 존재하지 않는 사용자가 이용하려고
            // 하는 경우 차단하기 위해서 이다.
            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser)
                return PutFavoriteResponseDto.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return PutFavoriteResponseDto.noExistBoard();

            FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);

            // 좋아요를 눌렀을때 기존에 누른 경우는 ++, 없는 경우에는 --;
            if (favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(email, boardNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();
            } else {
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();
            }

            boardRepository.save(boardEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PutFavoriteResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email) {
        
        try {

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return PatchBoardResponseDto.noExistBoard();

            boolean exitedUser = userRepository.existsByEmail(email);
            if(!exitedUser) return PatchBoardResponseDto.noExistUser();

            String writerEmail = boardEntity.getWriterEmail();
            boolean isWriter = writerEmail.equals(email);
            if(!isWriter) return PatchBoardResponseDto.noPermission();

            boardEntity.patchBoard(dto);
            boardRepository.save(boardEntity);

            //기존에 있던 이미지를 날리고 새롭게 받아온다.
            imageRepository.deleteByBoardNumber(boardNumber);
            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for(String image: boardImageList){
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }

            imageRepository.saveAll(imageEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PatchBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {
        try {
            // 게시글을 조회시에 조회수가 올라가야기 때문에 그에 대한 코드를 작성한다.
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return IncreaseViewCountResponseDto.noExistBoard();

            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return IncreaseViewCountResponseDto.success();
    }

    @Override
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {
        try {
            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser)
                return DeleteBoardResponseDto.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return DeleteBoardResponseDto.noExistBoard();

            String writerEmail = boardEntity.getWriterEmail();
            boolean isWriter = writerEmail.equals(email);
            if (!isWriter)
                return DeleteBoardResponseDto.noPermission();

            imageRepository.deleteByBoardNumber(boardNumber);
            commentRePository.deleteByBoardNumber(boardNumber);
            favoriteRepository.deleteByBoardNumber(boardNumber);
            boardRepository.delete(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteBoardResponseDto.success();
    }


}
