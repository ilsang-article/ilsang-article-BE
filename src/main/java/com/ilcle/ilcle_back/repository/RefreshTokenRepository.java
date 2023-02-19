package com.ilcle.ilcle_back.repository;

import com.ilcle.ilcle_back.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberUsername(String usernameFromToken);
    void deleteByMemberUsername(String username);

    Optional<RefreshToken> findById(Long id);

}