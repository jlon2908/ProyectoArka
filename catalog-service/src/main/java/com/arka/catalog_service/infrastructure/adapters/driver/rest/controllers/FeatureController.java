package com.arka.catalog_service.infrastructure.adapters.driver.rest.controllers;

import com.arka.catalog_service.application.ports.input.FeatureServicePort;
import com.arka.catalog_service.domain.model.Feature;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/features")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureServicePort featureService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Feature> create(@RequestBody Feature feature) {
        return featureService.create(feature);
    }

    @PutMapping("/{id}")
    public Mono<Feature> update(@PathVariable Long id, @RequestBody Feature feature) {
        return featureService.update(id, feature);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return featureService.delete(id);
    }

    @GetMapping("/{id}")
    public Mono<Feature> getById(@PathVariable Long id) {
        return featureService.getById(id);
    }

    @GetMapping
    public Flux<Feature> getAll() {
        return featureService.getAll();
    }



}
