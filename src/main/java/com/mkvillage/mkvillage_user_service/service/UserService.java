package com.mkvillage.mkvillage_user_service.service;

import com.mkvillage.mkvillage_user_service.entity.RoleRequest;
import com.mkvillage.mkvillage_user_service.entity.User;
import com.mkvillage.mkvillage_user_service.repository.RoleRequestRepository;
import com.mkvillage.mkvillage_user_service.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRequestRepository roleRequestRepository;

    // ====================================
    // GET PROFILE
    // ====================================
    public User getProfile(String mobile) {

        return userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // ====================================
    // REQUEST DAIRY OWNER ROLE
    // ====================================
    public void raiseDairyOwnerRequest(String mobile) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 🔐 If already dairy owner
        if (user.getRoles().contains("ROLE_DAIRY_OWNER")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You are already a Dairy Owner"
            );
        }

        // 🔐 If already pending request
        boolean alreadyPending =
                roleRequestRepository.existsByUserIdAndStatus(
                        user.getId(),
                        "PENDING"
                );

        if (alreadyPending) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Your request is already pending approval"
            );
        }

        // ✅ Create new request
        RoleRequest request = new RoleRequest();
        request.setUser(user);
        request.setRequestedRole("ROLE_DAIRY_OWNER");
        request.setStatus("PENDING");

        roleRequestRepository.save(request);
    }
}
