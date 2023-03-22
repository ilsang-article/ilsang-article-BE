package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

	// 전체글 조회(최신순)
	Page<Post> getAllPosts(Pageable pageable);

	Page<Post> findFilterByMember(Member member, Pageable pageable, Boolean read);


}
