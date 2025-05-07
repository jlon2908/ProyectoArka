package com.arka.catalog_service.application.exception.Feature;

public class FeatureNotFoundException extends RuntimeException {
    public FeatureNotFoundException(Long id) {

        super("Feature with ID " + id + " not found");
    }
    public FeatureNotFoundException(String name) {
        super("Feature with name '" + name + "' not found");
    }
}