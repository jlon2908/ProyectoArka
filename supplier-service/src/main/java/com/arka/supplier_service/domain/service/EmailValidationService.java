package com.arka.supplier_service.domain.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailValidationService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
