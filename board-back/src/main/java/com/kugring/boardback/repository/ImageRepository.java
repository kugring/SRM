package com.kugring.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.kugring.boardback.entity.ImageEntity;

import jakarta.transaction.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer>{

    //원래 Int로 해도 되지만 본인은 Integer가 편해서 그렇게 했다
    List<ImageEntity> findByBoardNumber(Integer boardNumber);

    @Transactional //데이터 삭제 오류시 해당 데이터를 다시 롤백해주는 트랙션널 이노테이션이다!
    void deleteByBoardNumber(Integer boardNumber);
}
