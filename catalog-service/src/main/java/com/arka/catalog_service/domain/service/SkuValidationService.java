package com.arka.catalog_service.domain.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class SkuValidationService {
    // SKU válido: BRAND-TYPE-MODEL[-COLOR] donde:
    // BRAND y TYPE: 2-5 letras
    // MODEL: alfanumérico con guiones permitido, min 3 caracteres
    // COLOR: opcional, 2-5 letras
    private static final Pattern SKU_PATTERN =
            Pattern.compile("^[A-Z]{2,5}-[A-Z]{2,5}-[A-Z0-9\\-]{3,}(?:-[A-Z]{2,5})?$");

    public String standardize(String rawSku) {
        if (rawSku == null || rawSku.isBlank()) {
            throw new IllegalArgumentException("SKU cannot be empty");
        }

        String cleaned = rawSku.trim().toUpperCase();

        cleaned = cleaned.replaceAll("[^A-Z0-9]", " ");

        cleaned = cleaned.replaceAll("\\s{2,}", " ").trim();

        String[] parts = cleaned.split(" ");

        if (parts.length < 3) {
            throw new IllegalArgumentException("SKU must have at least BRAND, TYPE, and MODEL");
        }

        // Construir el SKU
        String brand = parts[0];
        String type = parts[1];
        String model = parts[2];
        String color = parts.length > 3 ? parts[3] : null;

        // Validaciones básicas
        if (!brand.matches("^[A-Z]{2,5}$")) throw new IllegalArgumentException("Invalid BRAND format");
        if (!type.matches("^[A-Z]{2,5}$")) throw new IllegalArgumentException("Invalid TYPE format");
        if (!model.matches("^[A-Z0-9\\-]{3,}$")) throw new IllegalArgumentException("Invalid MODEL format");
        if (color != null && !color.matches("^[A-Z]{2,5}$")) throw new IllegalArgumentException("Invalid COLOR format");

        StringBuilder result = new StringBuilder();
        result.append(brand).append("-").append(type).append("-").append(model);
        if (color != null) result.append("-").append(color);

        String formattedSku = result.toString();

        if (!isValid(formattedSku)) {
            throw new IllegalArgumentException("Final SKU format is invalid: " + formattedSku);
        }

        return formattedSku;
    }


    public boolean isValid(String sku) {
        if (sku == null) return false;
        return SKU_PATTERN.matcher(sku.trim()).matches();
    }

}
