package com.arka.catalog_service.application.exception.Brand;

public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException(Long id) {
        super("Brand with ID " + id + " not found");


    }

    public BrandNotFoundException(String name) {
        super("Brand with name '" + name + "' not found");
    }
}
