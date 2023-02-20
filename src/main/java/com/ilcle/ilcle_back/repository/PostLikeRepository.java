package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByMemberAndPostId(Member member, Long postId);
}
