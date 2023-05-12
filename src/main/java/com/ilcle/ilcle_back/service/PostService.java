package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.response.PostResponseDto;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostLike;
import com.ilcle.ilcle_back.repository.PostLikeRepository;
import com.ilcle.ilcle_back.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostLikeRepository postLikeRepository;
	private final PostRepository postRepository;

	//전체 글 조회, 검색
	public Page<PostResponseDto> getPosts(Pageable pageable,String search, String username) {

		Page<Post> posts = postRepository.getAllPosts(pageable,search);

		List<PostResponseDto> postList = new ArrayList<>();
		for(Post post : posts) {
			Optional<PostLike> postLike = postLikeRepository.findByPostIdAndMemberUsername(post.getId(), username);
			boolean postLikeCheck = postLike.isPresent();

			PostResponseDto postResponseDto =
					PostResponseDto.builder()
							.id(post.getId())
							.title(post.getTitle())
							.contents(post.getContents())
							.url(post.getUrl())
							.imageUrl(post.getImageUrl())
							.writeDate(post.getWriteDate())
							.writer(post.getWriter())
							.likeCheck(postLikeCheck)
							.test("test")
							.build();
			postList.add(postResponseDto);
		}
		return new PageImpl<>(postList,pageable,posts.getTotalElements());

	}
}
