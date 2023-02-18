package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.dto.response.AllPostResponseDto;
import com.ilcle.ilcle_back.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Page<AllPostResponseDto> getAllPosts(Pageable pageable);
}
