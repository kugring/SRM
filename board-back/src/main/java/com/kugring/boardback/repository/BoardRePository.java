package com.kugring.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kugring.boardback.entity.BoardEntity;
import com.kugring.boardback.repository.resultSet.GetBoardResultSet;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{
    
    boolean existsByBoardNumber(Integer boardNumber);

    BoardEntity findByBoardNumber(Integer boardNumber);

    // DML에 있는 쿼리를 그대로 가져왔고
    // 주의점으로는 반드시 문자 뒤에 " " 공백이 존재해야한다!
    @Query(
        value=
        "SELECT " +
        "B.board_number AS boardNumber, " +
        "B.title AS title, " +
        "B.content AS content, " +
        "B.write_datetime AS writeDatetime, " +
        "B.writer_email AS writerEmail, " +
        "U.nickname AS writerNickname, " +
        "U.profile_image AS writerProfileImage " +
        "FROM board AS B " +
        "INNER JOIN user AS U " +
        "ON B.writer_email = U.email " +
        "WHERE board_number = ?1 ", // "?1"를 넣은것은 하단에 getBoard의 첫번째 파라미터로 값을 넣겠다는 의미이다.
        nativeQuery = true
    )
    // 보통은 BoardEntity타입으로 데이터를 넣어야하지만
    // Join된 테이블의 상태이기 때문에 따로 ResultSet의 인터페이스를 만들어서 date타입과 프로필이미지 데이터형태를 지정해준다.
    GetBoardResultSet getBoard(Integer boardNumber);
}
