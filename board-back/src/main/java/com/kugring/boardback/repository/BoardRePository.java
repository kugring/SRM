package com.kugring.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kugring.boardback.entity.BoardEntity;

@Repository
public interface BoardRePository extends JpaRepository<BoardEntity, Integer>{
    
}
