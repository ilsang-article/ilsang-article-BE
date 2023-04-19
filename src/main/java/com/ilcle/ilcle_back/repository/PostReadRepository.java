package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.PostRead;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostReadRepository extends JpaRepository<PostRead, Long> {
    Optional<PostRead> findByMemberUsernameAndPostId(String username, Long postId);
    void deleteByMemberIdAndPostId(Long memberId, Long postId);
}
