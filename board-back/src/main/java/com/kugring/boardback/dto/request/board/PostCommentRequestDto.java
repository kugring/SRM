package com.kugring.boardback.dto.request.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentRequestDto {
    
    // 참고로 int타입이나 boolean타입에서는 NotBlank를 사용하지 못한다.
    @NotBlank
    private String content;

}
