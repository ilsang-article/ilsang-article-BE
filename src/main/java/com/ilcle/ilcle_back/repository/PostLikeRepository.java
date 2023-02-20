package com.ilcle.ilcle_back.repository;


import com.ilcle.ilcle_back.dto.response.MyPostResponseDto;
import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.Post;
import com.ilcle.ilcle_back.entity.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

	Optional<PostLike> findByPostIdAndMemberUsername(Long postId, String username);

	PostLike findByMemberAndPostId(Member member, Long postId);

	Page<PostLike> findPostLikesByMember(Member member, Pageable pageable);
}