package com.ilcle.ilcle_back;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleRequestDto {

    @Schema(description = "유저아이디", example = "1")
    String userId;

    @NotNull
    @Schema(description = "유저패스워드", example = "1234")
    String password;

    @Null
    @Schema(description = "유저메모", example = "메모")
    String memo;

    @Schema(description = "테스트 숫자", example = "22", maximum = "50")
    Long testNum;
}