package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);


    boolean existsByUsername(String username);
}
