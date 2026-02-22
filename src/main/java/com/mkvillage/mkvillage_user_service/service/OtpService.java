package com.mkvillage.mkvillage_user_service.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.mkvillage.mkvillage_user_service.config.TwilioConfig;
import com.mkvillage.mkvillage_user_service.entity.OtpVerification;
import com.mkvillage.mkvillage_user_service.repository.OtpRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final TwilioConfig twilioConfig;

    public void sendOtp(String mobile) {

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        OtpVerification entity = new OtpVerification();
        entity.setMobile(mobile);
        entity.setOtp(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        entity.setUsed(false);

        otpRepository.save(entity);

        Message.creator(
                new PhoneNumber(mobile),
                new PhoneNumber(twilioConfig.getPhoneNumber()),
                "Your MKVillage OTP is: " + otp
        ).create();
    }

    public void validateOtp(String mobile, String otp) {

        OtpVerification entity = otpRepository
                .findTopByMobileOrderByIdDesc(mobile)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (entity.isUsed()) {
			throw new RuntimeException("OTP already used");
		}

        if (!entity.getOtp().equals(otp)) {
			throw new RuntimeException("Invalid OTP");
		}

        if (entity.getExpiryTime().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP expired");
		}

        entity.setUsed(true);
        otpRepository.save(entity);
    }
}
