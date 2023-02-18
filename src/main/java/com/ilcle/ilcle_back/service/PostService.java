package com.ilcle.ilcle_back.service;

import com.ilcle.ilcle_back.dto.response.AllPostResponseDto;
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

	public Page<AllPostResponseDto> getAllPosts(Pageable pageable) {
		return PostRepository.getAllPost(pageable);
	}


}
