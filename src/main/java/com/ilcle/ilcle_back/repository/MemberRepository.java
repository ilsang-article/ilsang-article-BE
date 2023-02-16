package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByKakaoId(String kakaoId);

    boolean existsByNickname(String nickname);

    boolean existsByUsername(String username);
}
