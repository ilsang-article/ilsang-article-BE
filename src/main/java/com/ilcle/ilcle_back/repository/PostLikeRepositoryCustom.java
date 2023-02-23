package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostLikeRepositoryCustom {

	Page<Post> findFilterByMember(Member member, Pageable pageable, Boolean read);
}
