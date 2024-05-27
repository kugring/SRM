package com.kugring.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.boardback.entity.FavoriteEntity;
import com.kugring.boardback.entity.primaryKey.FavoritePk;

@Repository
//favorite 테이블의 id가 두개인데 하나는 string 하나는 int이기 때문에 pk의 타입을 만들어줘야 한다.
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk>{
    
}
