package com.arka.catalog_service;

import com.arka.catalog_service.application.ports.output.BrandPersistencePort;
import com.arka.catalog_service.application.usecase.BrandServiceImpl;
import com.arka.catalog_service.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

public class BrandServiceImplTest {

    private BrandPersistencePort persistencePort;
    private BrandServiceImpl brandService;

    @BeforeEach
    public void setUp() {
        persistencePort = Mockito.mock(BrandPersistencePort.class);
        brandService = new BrandServiceImpl(persistencePort);
    }

    @Test
    public void testCreateBrand() {
        Brand brand = new Brand(null, "Adidas");
        Brand savedBrand = new Brand(1L, "Adidas");

        when(persistencePort.existsByNombre("Adidas")).thenReturn(Mono.just(false)); // 👈 agregar esto
        when(persistencePort.save(brand)).thenReturn(Mono.just(savedBrand));

        StepVerifier.create(brandService.create(brand))
                .expectNextMatches(result -> result.getId() == 1L && result.getNombre().equals("Adidas"))
                .verifyComplete();

        verify(persistencePort).existsByNombre("Adidas"); // 👈 verificar también esto
        verify(persistencePort).save(brand);
    }


    @Test
    public void testGetAllBrands() {
        Brand brand1 = new Brand(1L, "Adidas");
        Brand brand2 = new Brand(2L, "Nike");

        when(persistencePort.findAll()).thenReturn(Flux.fromIterable(List.of(brand1, brand2)));

        StepVerifier.create(brandService.getAll())
                .expectNext(brand1)
                .expectNext(brand2)
                .verifyComplete();

        verify(persistencePort).findAll();
    }
}