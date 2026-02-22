package com.mkvillage.mkvillage_user_service.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mkvillage.mkvillage_user_service.dto.UserResponseDto;
import com.mkvillage.mkvillage_user_service.entity.User;
import com.mkvillage.mkvillage_user_service.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class InternalUserService {

    @Autowired
    private UserRepository userRepository;

    // ====================================
    // GET USER BY MOBILE
    // ====================================
    public UserResponseDto getUserByMobile(String mobile) {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found with mobile: " + mobile));

        return mapToDto(user);
    }

    // ====================================
    // GET USER BY ID
    // ====================================
    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found with id: " + id));

        return mapToDto(user);
    }

    // ====================================
    // CHECK IF USER HAS ROLE
    // ====================================
    public boolean hasRole(Long userId, String role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"));

        Set<String> roles = user.getRoles();

        for (String r : roles) {
            if (r.equalsIgnoreCase(role)) {
                return true;
            }
        }

        return false;
    }

    // ====================================
    // MAP ENTITY TO DTO
    // ====================================
    private UserResponseDto mapToDto(User user) {

        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setMobile(user.getMobile());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setRoles(user.getRoles());

        return dto;
    }
}
