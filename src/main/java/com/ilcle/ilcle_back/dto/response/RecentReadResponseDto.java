package com.ilcle.ilcle_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecentReadResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String url;
    private String imageUrl;
    private String writeDate;
    private String writer;
    private boolean likeCheck;
}
