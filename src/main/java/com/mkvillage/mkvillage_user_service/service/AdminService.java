package com.mkvillage.mkvillage_user_service.service;

import com.mkvillage.mkvillage_user_service.entity.RoleRequest;
import com.mkvillage.mkvillage_user_service.entity.User;
import com.mkvillage.mkvillage_user_service.repository.RoleRequestRepository;
import com.mkvillage.mkvillage_user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final RoleRequestRepository roleRequestRepository;
	private final UserRepository userRepository;

	// ===============================
	// GET ALL USERS
	// ===============================
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// ===============================
	// GET PENDING REQUESTS
	// ===============================
	public List<RoleRequest> getPendingRequests() {
		return roleRequestRepository.findByStatus("PENDING");
	}

	// ===============================
	// APPROVE DAIRY OWNER
	// ===============================
	@Transactional
	public void approveDairyOwner(Long userId) {

		RoleRequest request = roleRequestRepository.findByUserIdAndStatus(userId, "PENDING")
				.orElseThrow(() -> new RuntimeException("Request not found"));

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// 🔐 Add role, DO NOT replace
		user.getRoles().add("ROLE_DAIRY_OWNER");

		request.setStatus("APPROVED");
	}

	// ===============================
	// REJECT DAIRY OWNER
	// ===============================
	@Transactional
	public void rejectDairyOwner(Long userId) {

		RoleRequest request = roleRequestRepository.findByUserIdAndStatus(userId, "PENDING")
				.orElseThrow(() -> new RuntimeException("Request not found"));

		request.setStatus("REJECTED");
	}

	// ===============================
	// SAFE ROLE CHANGE
	// ===============================
	@Transactional
	public void changeUserRole(Long userId, String action) {

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// 🔐 Admin role cannot be modified
		if (user.getRoles().contains("ROLE_ADMIN")) {
			throw new RuntimeException("Admin role cannot be modified");
		}

		switch (action) {

		case "ADD_DAIRY_OWNER":
			user.getRoles().add("ROLE_DAIRY_OWNER");
			break;

		case "REMOVE_DAIRY_OWNER":
			user.getRoles().remove("ROLE_DAIRY_OWNER");
			break;

		default:
			throw new RuntimeException("Invalid role action");
		}

		// 🔒 Ensure FARMER role always exists
		if (!user.getRoles().contains("ROLE_FARMER")) {
			user.getRoles().add("ROLE_FARMER");
		}
	}

}
