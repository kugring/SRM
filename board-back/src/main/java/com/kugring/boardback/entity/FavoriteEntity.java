package com.kugring.boardback.entity;

import com.kugring.boardback.entity.primaryKey.FavoritePk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="favortie")
@Table(name ="favortie")
@IdClass(FavoritePk.class) //id가 어떤 클래스를 따르는지 지정해준다. 파라미터에는 대항 따르는 클래스를 넣어준다.
public class FavoriteEntity {

    @Id
    private String userEmail;
    @Id // 복합 key이기 때문에 둘다 id로 지정해준다.
    private int boardNumber;
    
}
