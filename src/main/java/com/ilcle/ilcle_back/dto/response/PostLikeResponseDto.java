package com.ilcle.ilcle_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLikeResponseDto {

	private boolean postLikeCheck;
}
