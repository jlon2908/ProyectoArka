package com.arka.supplier_service.domain.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PhoneValidationService {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+)?\\d{7,15}$");

    public static boolean isValidPhone(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

}
