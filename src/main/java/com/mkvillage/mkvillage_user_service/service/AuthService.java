package com.mkvillage.mkvillage_user_service.service;

import com.mkvillage.mkvillage_user_service.dto.LoginRequest;
import com.mkvillage.mkvillage_user_service.dto.RegisterRequest;
import com.mkvillage.mkvillage_user_service.dto.RegisterVerifyRequest;
import com.mkvillage.mkvillage_user_service.entity.TokenBlacklist;
import com.mkvillage.mkvillage_user_service.entity.User;
import com.mkvillage.mkvillage_user_service.repository.TokenBlacklistRepository;
import com.mkvillage.mkvillage_user_service.repository.UserRepository;
import com.mkvillage.mkvillage_user_service.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    // =====================================
    // SEND OTP
    // =====================================
    public void sendOtp(RegisterRequest request) {

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Mobile already registered"
            );
        }

        otpService.sendOtp(request.getMobile());
    }

    // =====================================
    // VERIFY OTP & REGISTER
    // =====================================
    public void verifyOtp(RegisterVerifyRequest request) {

        // Validate OTP
        otpService.validateOtp(request.getMobile(), request.getOtp());

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Mobile already registered"
            );
        }

        User user = new User();
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMobileVerified(true);
        user.setActive(true);

        // Default role
        user.getRoles().add("ROLE_FARMER");

        userRepository.save(user);
    }

    // =====================================
    // LOGIN
    // =====================================
    public String login(LoginRequest request) {

        User user = userRepository.findByMobile(request.getMobile())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid credentials"
            );
        }

        if (!user.isMobileVerified()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Mobile not verified"
            );
        }

        if (!user.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Account is disabled"
            );
        }

        // Generate JWT with roles
        return jwtUtil.generateToken(user.getMobile(), user.getRoles());
    }

    // =====================================
    // LOGOUT (Blacklist Token)
    // =====================================
    public void logout(String token) {

        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);

        tokenBlacklistRepository.save(blacklist);
    }
}
