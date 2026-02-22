package com.mkvillage.mkvillage_user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkvillage.mkvillage_user_service.entity.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {

    boolean existsByToken(String token);
}
