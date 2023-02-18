package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "포스트 컨트롤러")
@RequiredArgsConstructor
@RestController
public class PostController {

	// 전체글 조회(최신순)
	@GetMapping("/posts")
	public ResponseDto<?> getAllposts(Pageable pageable) {
		return null;
	}

}
