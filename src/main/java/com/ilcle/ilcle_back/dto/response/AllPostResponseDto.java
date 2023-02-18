package com.ilcle.ilcle_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllPostResponseDto {

	private Long id;
	private String title;
	private String contents;
	private String url;
	private String imageUrl;
	private String writeDate;
	private String writer;

}
