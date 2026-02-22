package com.mkvillage.mkvillage_user_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mkvillage.mkvillage_user_service.entity.RoleRequest;

@Repository
public interface RoleRequestRepository extends JpaRepository<RoleRequest, Long> {

    List<RoleRequest> findByStatus(String status);

    List<RoleRequest> findByUserId(Long userId);

    boolean existsByUserIdAndStatus(Long userId, String status);
    
    

    // 🔥 ADD THIS
    java.util.Optional<RoleRequest> findByUserIdAndStatus(Long userId, String status);
}
