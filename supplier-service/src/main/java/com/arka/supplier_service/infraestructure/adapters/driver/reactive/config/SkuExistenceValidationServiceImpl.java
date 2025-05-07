package com.arka.supplier_service.infraestructure.adapters.driver.reactive.config;

import com.arka.supplier_service.domain.exception.supplierOrder.SkuNotFoundException;
import com.arka.supplier_service.domain.service.SkuExistenceValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkuExistenceValidationServiceImpl implements SkuExistenceValidationService {

    private final WebClient catalogWebClient;
    @Override
    public Mono<Void> validateSkusExist(List<String> skus) {
        return Mono.when(
                skus.stream()
                        .map(sku -> catalogWebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                        .path("/api/products/filter/sku")
                                        .queryParam("sku", sku)
                                        .build())
                                .retrieve()
                                .bodyToMono(Void.class)
                                .onErrorResume(WebClientResponseException.NotFound.class,
                                        ex -> Mono.error(new SkuNotFoundException(sku)))
                        ).toList()
        );
    }    }
 
