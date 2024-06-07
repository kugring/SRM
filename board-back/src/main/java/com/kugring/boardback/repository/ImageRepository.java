package com.kugring.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.kugring.boardback.entity.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer>{

    //원래 Int로 해도 되지만 본인은 Integer가 편해서 그렇게 했다
    List<ImageEntity> findByBoardNumber(Integer boardNumber);
    
}
