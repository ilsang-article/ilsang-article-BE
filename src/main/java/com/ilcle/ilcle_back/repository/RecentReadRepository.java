package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.RecentRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RecentReadRepository extends JpaRepository<RecentRead, Long> {
    List<RecentRead> findRecentReadById(Long memberId);
    List<RecentRead> findRecentReadCheckById(Long memberId);
}
