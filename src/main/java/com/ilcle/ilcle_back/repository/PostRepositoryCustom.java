package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

	// 전체글 조회(최신순)
	Page<PostResponseDto> getAllPosts(Pageable pageable);
}
