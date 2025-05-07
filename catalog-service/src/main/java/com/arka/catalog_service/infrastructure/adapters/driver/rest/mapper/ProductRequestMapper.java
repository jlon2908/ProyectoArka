package com.arka.catalog_service.infrastructure.adapters.driver.rest.mapper;

import com.arka.catalog_service.domain.model.Brand;
import com.arka.catalog_service.domain.model.Category;
import com.arka.catalog_service.domain.model.Product;
import com.arka.catalog_service.domain.model.ProductFeature;
import com.arka.catalog_service.infrastructure.adapters.driver.rest.dto.CreateProductRequest;
import com.arka.catalog_service.infrastructure.utils.BrandFinder;
import com.arka.catalog_service.infrastructure.utils.CategoryFinder;
import com.arka.catalog_service.infrastructure.utils.FeatureFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRequestMapper {

    private final BrandFinder brandFinder;
    private final CategoryFinder categoryFinder;
    private final FeatureFinder featureFinder;

    public Mono<Product> toDomain(CreateProductRequest request) {
        Mono<Brand> brandMono = brandFinder.findByName(request.getBrandName())
                .switchIfEmpty(Mono.error(new RuntimeException("Brand not found: " + request.getBrandName())));

        Mono<List<Category>> categoriesMono = Flux.fromIterable(request.getCategoryNames())
                .flatMap(name ->
                        categoryFinder.findByName(name)
                                .switchIfEmpty(Mono.error(new RuntimeException("Category not found: " + name))))
                .collectList();

        Mono<List<ProductFeature>> featuresMono = Flux.fromIterable(request.getFeatures())
                .flatMap(fv ->
                        featureFinder.findByName(fv.getFeatureName())
                                .switchIfEmpty(Mono.error(new RuntimeException("Feature not found: " + fv.getFeatureName())))
                                .map(feature -> new ProductFeature(null, feature.getId(), fv.getValue())))
                .collectList();

        return Mono.zip(brandMono, categoriesMono, featuresMono)
                .map(tuple -> {
                    Brand brand = tuple.getT1();
                    List<Category> categories = tuple.getT2();
                    List<ProductFeature> features = tuple.getT3();

                    return new Product(
                            null,
                            request.getName(),
                            request.getDescription(),
                            request.getUnitPrice(),
                            request.getSku(),
                            true,
                            LocalDateTime.now(),
                            brand,
                            categories,
                            features
                    );
                })
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.error(e);
                });
    }
}
