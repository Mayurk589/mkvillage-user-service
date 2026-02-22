package com.mkvillage.mkvillage_user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkvillage.mkvillage_user_service.entity.OtpVerification;

public interface OtpRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findTopByMobileOrderByIdDesc(String mobile);
}
