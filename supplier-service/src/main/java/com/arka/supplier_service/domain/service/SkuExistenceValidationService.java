package com.arka.supplier_service.domain.service;

import reactor.core.publisher.Mono;

import java.util.List;


public interface SkuExistenceValidationService {
    Mono<Void> validateSkusExist(List<String> skus);
}
