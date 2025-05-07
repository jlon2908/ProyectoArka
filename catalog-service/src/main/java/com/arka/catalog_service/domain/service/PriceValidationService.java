package com.arka.catalog_service.domain.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PriceValidationService {
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");

    public boolean isValidPrice(String price) {
        return price != null && PRICE_PATTERN.matcher(price).matches();
    }
}
