package com.kugring.boardback.entity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import com.kugring.boardback.dto.request.board.PostBoardRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board")
@Table(name = "board")
public class BoardEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//자동생성관련 어노테이션 
    private int boardNumber;
    // @Colum(name="~~~") 원래는 하나하나씩 맵핑을 해줘야하지만 "명령규칙"만 잘 지킨다면 그대로 사용해도 괜찮다.
    private String title;
    private String content;
    private String writeDatetime;
    private int favoriteCount;
    private int commentCount;
    private int viewCount;
    private String writerEmail;


    // 직접 BoardServiceImplement.java에서 사용할 코드를 작정! (빌더를? 안쓰고 일부로 직접 작성했다는데...?이해는 안감)
    public BoardEntity(PostBoardRequestDto dto, String email){

        // 자바 util것의 Date를 임폴트!
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        System.out.println("보드 파일 빌더 대신 사용 보더엔티티 파일");
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writeDatetime = writeDatetime;
        this.favoriteCount = 0;
        this.commentCount = 0;
        this.viewCount = 0;
        this.writerEmail = email;
    }
}
