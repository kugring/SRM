package com.kugring.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kugring.boardback.entity.CommentEntity;
import com.kugring.boardback.repository.resultSet.GetCommentListResultSet;

import jakarta.transaction.Transactional;

public interface CommentRePository extends JpaRepository<CommentEntity, Integer> {

    @Query(value = "SELECT " +
            "    U.nickname AS nickname, " +
            "    U.profile_image AS profileImage, " +
            "    C.write_datetime AS writeDatetime, " +
            "    C.content AS content " +
            "FROM comment AS C " +
            "INNER JOIN user AS U " +
            "ON C.user_email = U.email " +
            "WHERE C.board_number = ?1 " +
            "ORDER BY writeDatetime DESC ", nativeQuery = true)
    List<GetCommentListResultSet> getCommentList(Integer boardNumber);

    @Transactional // 데이터 삭제 오류시 해당 데이터를 다시 롤백해주는 트랙션널 이노테이션이다!
    void deleteByBoardNumber(Integer boardNumber);
}
