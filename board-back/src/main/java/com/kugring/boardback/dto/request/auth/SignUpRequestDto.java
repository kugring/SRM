package com.kugring.boardback.dto.request.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    
    @NotBlank @Email //null이 아니면서 공백도 아닌것
    private String email;

    @NotBlank @Size(min=8, max=20)
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank(message = "이메일은 공백일 수 없습니다") @Pattern(regexp="^[0-9]{11,13}$")
    private String telNumber;

    @NotBlank
    private String address;

    private String addressDetial;

    @NotNull @AssertTrue // true가 아니면 데이터를 받지 않도록한다.
    private Boolean agreedPersonal; // 참수형 타입에서는 가능하다.
}
//