package com.kugring.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kugring.boardback.entity.CommentEntity;
import com.kugring.boardback.repository.resultSet.GetCommentListResultSet;

public interface CommentRePository extends JpaRepository<CommentEntity, Integer>{
    
    @Query(
        value=
        "SELECT " + 
        "    U.nickname AS nickname, " +
        "    U.profile_image AS profileImage, " +
        "    C.write_datetime AS writerDatetime, " +
        "    C.content AS content " +
        "FROM comment AS C " +
        "INNER JOIN user AS U " +
        "ON C.user_email = U.email " +
        "WHERE C.board_number = ?1 " +
        "ORDER BY writerDatetime DESC ",
        nativeQuery = true
    )
    List<GetCommentListResultSet> getCommentList(Integer boardNumber);
    
}
