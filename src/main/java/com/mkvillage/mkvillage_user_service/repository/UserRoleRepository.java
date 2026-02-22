package com.mkvillage.mkvillage_user_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkvillage.mkvillage_user_service.entity.UserRole;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	boolean existsByUserIdAndRole(Long userId, String role);
	
	Optional<UserRole> findByUserIdAndRole(Long userId, String role);
	
	List<UserRole> findByUserId(Long userId);

}
