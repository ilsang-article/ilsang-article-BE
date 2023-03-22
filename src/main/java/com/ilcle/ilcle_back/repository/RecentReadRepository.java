package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.Member;
import com.ilcle.ilcle_back.entity.RecentRead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RecentReadRepository extends JpaRepository<RecentRead, Long> {
    Optional<RecentRead> findByMemberUsernameAndPostId(String username, Long postId);

    Page<RecentRead> findAllByMember(Member member, Pageable pageable);
    List<RecentRead> findAllByMember(Member member);
}
