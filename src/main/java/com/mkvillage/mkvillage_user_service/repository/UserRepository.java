package com.mkvillage.mkvillage_user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkvillage.mkvillage_user_service.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMobile(String mobile);
    
    Optional<User> findByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);
}
