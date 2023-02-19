package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.response.PostResponseDto;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	public Page<PostResponseDto> getAllPosts(Pageable pageable) {
		return postRepository.getAll(pageable); // Spring Data JPA의 findAll() 메소드:  모든 엔티티를 조회. 페이징, 정렬
	}


}
