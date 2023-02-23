package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.response.PostLikeResponseDto;
import com.ilcle.ilcle_back.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

	private final PostLikeService postLikeService;

	@PostMapping("/posts/{postId}/like")

	public ResponseDto<PostLikeResponseDto> postLikeUp(@PathVariable Long postId,
													   @AuthenticationPrincipal UserDetails userDetails) {
		return postLikeService.postLikeUp(postId, userDetails.getUsername());
	}
}
