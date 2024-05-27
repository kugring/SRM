package com.kugring.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kugring.boardback.entity.CommentEntity;

public interface CommentRePository extends JpaRepository<CommentEntity, Integer>{
    
    
}
