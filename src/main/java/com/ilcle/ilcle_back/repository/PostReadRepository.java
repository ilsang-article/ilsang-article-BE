package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.PostRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostReadRepository extends JpaRepository<PostRead, Long> {
    Optional<PostRead> findByPostIdAndMemberId(Long postId, Long memberId);
}
