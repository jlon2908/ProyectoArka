package com.arka.catalog_service;

import com.arka.catalog_service.application.ports.input.BrandServicePort;
import com.arka.catalog_service.application.ports.output.BrandPersistencePort;
import com.arka.catalog_service.application.usecase.BrandServiceImpl;
import com.arka.catalog_service.domain.model.Brand;
import com.arka.catalog_service.infrastructure.adapters.driver.rest.controllers.BrandController;
import com.arka.catalog_service.infrastructure.config.GlobalExceptionHandler;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = BrandController.class)
@Import({BrandControllerTest.TestConfig.class, GlobalExceptionHandler.class})
class BrandControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BrandPersistencePort brandPersistencePort;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BrandServicePort brandService(BrandPersistencePort port) {
            return new BrandServiceImpl(port);
        }
    }

    @Test
    void shouldCreateBrandSuccessfully() {
        Brand newBrand = new Brand(null, "hp");
        Brand savedBrand = new Brand(1L, "hp");

        Mockito.when(brandPersistencePort.existsByNombre(Mockito.anyString()))
                .thenReturn(Mono.just(false));

        Mockito.when(brandPersistencePort.save(Mockito.any(Brand.class)))
                .thenReturn(Mono.just(savedBrand));

        webTestClient.post()
                .uri("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newBrand)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("hp");
    }

    @Test
    void shouldReturnConflictWhenDuplicateBrand() {
        Brand duplicateBrand = new Brand(null, "hp");

        Mockito.when(brandPersistencePort.existsByNombre(Mockito.anyString()))
                .thenReturn(Mono.just(true));

        webTestClient.post()
                .uri("/api/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(duplicateBrand)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.message").value(Matchers.containsString("already exists"));
    }
}
