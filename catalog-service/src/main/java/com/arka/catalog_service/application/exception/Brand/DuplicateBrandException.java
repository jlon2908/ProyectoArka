package com.arka.catalog_service.application.exception.Brand;

public class DuplicateBrandException extends RuntimeException {
    public DuplicateBrandException(String nombre) {
        super("Brand with name '" + nombre + "' already exists.");
    }
}
