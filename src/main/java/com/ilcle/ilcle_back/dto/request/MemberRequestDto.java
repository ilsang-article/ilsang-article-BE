package com.ilcle.ilcle_back.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "Username은 공백일 수 없습니다.")
    @Pattern(regexp = "[a-z0-9]{4,16}$", message = "아이디 양식을 확인해주세요.")
    private String username;

    @NotBlank(message = "Password는 공백일 수 없습니다.")
    @Pattern(regexp = "[a-z0-9]{8,16}$", message = "패스워드 양식을 확인해주세요.")
    private String password;

    @NotBlank
    private String passwordConfirm;

}