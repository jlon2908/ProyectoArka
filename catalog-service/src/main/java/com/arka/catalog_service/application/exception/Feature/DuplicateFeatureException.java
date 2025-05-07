package com.arka.catalog_service.application.exception.Feature;


public class DuplicateFeatureException extends RuntimeException {
    public DuplicateFeatureException(String name) {
        super("Feature with name '" + name + "' already exists");
    }
}