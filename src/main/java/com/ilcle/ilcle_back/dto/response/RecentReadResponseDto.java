package com.ilcle.ilcle_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentReadResponseDto {

    private boolean read;

    private LocalDateTime modifiedAt;

}
