package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.ResponseDto;
import com.ilcle.ilcle_back.dto.request.PostLikeRequestDto;
import com.ilcle.ilcle_back.dto.response.PostLikeResponseDto;
import com.ilcle.ilcle_back.entity.PostLike;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.exception.ErrorCode;
import com.ilcle.ilcle_back.exception.GlobalException;
import com.ilcle.ilcle_back.repository.PostLikeRepository;
import com.ilcle.ilcle_back.repository.PostRepository;
import com.ilcle.ilcle_back.utils.ValidateCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.context.Theme;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

	private final PostLikeRepository postLikeRepository;
	private final PostRepository postRepository;
	private final ValidateCheck validateCheck;

	@Transactional
	public ResponseDto<PostLikeResponseDto> postLikeUp(Long postId, String username){

		Optional<PostLike> likes = postLikeRepository
				.findByPostIdAndMemberUsername(postId, username);

		Member member = validateCheck.getMember(username);
		Post post = new Post(postId);

		Post updatePost = postRepository.findById(post.getId()).orElseThrow(
				() -> new GlobalException(ErrorCode.POST_NOT_FOUND)
		);

		boolean postLikeCheck;
		if (likes.isPresent()) {
			postLikeCheck = false;
			postLikeRepository.delete(likes.get());
			updatePost.updatePostLikeCheck(false);
		} else {
			postLikeCheck = true;
			PostLike postLike = new PostLike(member, post);
			postLikeRepository.save(postLike);
			updatePost.updatePostLikeCheck(true);
		}

		return ResponseDto.success(
				PostLikeResponseDto.builder()
						.postLikeCheck(postLikeCheck)
						.build()
		);
	}
}
