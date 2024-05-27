package com.kugring.boardback.entity.primaryKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritePk implements Serializable{
    @Column(name="user_mail")
    private String userEmail;
    @Column(name="board_number")
    private int boardNumber;
}

// 해당 java파일은 favorite 테이블의 id값이 두개이고 둘이 데이터 타입이 다르기 때문에 따로 변수를 지정해 준것이다.