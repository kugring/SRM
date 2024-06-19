package com.kugring.boardback.dto.request.board;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class PatchBoardRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull //List형식은 blank가 아니라 null을 신경써야한다.
    private List<String> boardImageList;
}
