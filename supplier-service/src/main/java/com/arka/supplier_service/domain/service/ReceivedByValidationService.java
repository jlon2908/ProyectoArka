package com.arka.supplier_service.domain.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ReceivedByValidationService {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,}$");

    public boolean isValid(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }
}
