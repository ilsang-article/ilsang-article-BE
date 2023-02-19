package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.RecentRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RecentReadRepository extends JpaRepository<RecentRead, Long> {
    Optional<RecentRead> findByMemberUsernameAndPostId(String username, Long postId);

}
