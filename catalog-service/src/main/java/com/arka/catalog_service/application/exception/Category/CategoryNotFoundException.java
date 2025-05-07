package com.arka.catalog_service.application.exception.Category;


public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Category with ID " + id + " was not found.");
    }
    public CategoryNotFoundException(String name) {
        super("Category with name '" + name + "' not found");
    }
}