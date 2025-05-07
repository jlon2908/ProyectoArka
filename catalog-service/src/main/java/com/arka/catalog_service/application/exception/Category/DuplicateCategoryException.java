package com.arka.catalog_service.application.exception.Category;

public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException(String name) {
        super("Category with name '" + name + "' already exists.");
    }
}