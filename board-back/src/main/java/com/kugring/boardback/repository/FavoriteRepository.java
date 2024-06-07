package com.kugring.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kugring.boardback.entity.FavoriteEntity;
import com.kugring.boardback.entity.primaryKey.FavoritePk;
import com.kugring.boardback.repository.resultSet.GetFavoriteListResultSet;

@Repository
//favorite 테이블의 id가 두개인데 하나는 string 하나는 int이기 때문에 pk의 타입을 만들어줘야 한다.
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk>{
    
    FavoriteEntity findByBoardNumberAndUserEmail(Integer boardNumber, String userEmail);

    @Query(
        value=
        "   SELECT " + 
        "   U.email AS email, " +
        "   U.nickname AS nickname, " +
        "   U.profile_image AS profileImage " +
        "FROM favorite AS F "+
        "INNER JOIN user AS U " +
        "ON F.user_email = U.email " +
        "WHERE F.board_number = ?1",
        nativeQuery = true
    )
    List<GetFavoriteListResultSet> getFavoriteList(Integer boardNumber);
}
