package com.arka.catalog_service.application.usecase;

import com.arka.catalog_service.application.exception.product.DuplicateSkuException;
import com.arka.catalog_service.application.exception.product.SkuNotFoundException;
import com.arka.catalog_service.application.ports.input.ProductServicePort;
import com.arka.catalog_service.application.ports.output.ProductPersistencePort;
import com.arka.catalog_service.domain.model.Product;
import com.arka.catalog_service.domain.service.PriceValidationService;
import com.arka.catalog_service.domain.service.SkuValidationService;
import com.arka.catalog_service.infrastructure.config.ValidationErrorDetail;
import com.arka.catalog_service.infrastructure.config.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServicePort {

    private final ProductPersistencePort persistencePort;
    private final PriceValidationService priceValidationService;
    private  final SkuValidationService skuValidationService;

    @Override
    public Mono<Product> createProduct(Product product) {
        List<ValidationErrorDetail> errors = new ArrayList<>();

        String priceAsString = product.getUnitPrice().toString();
        if (!priceValidationService.isValidPrice(priceAsString)) {
            errors.add(new ValidationErrorDetail("unitPrice", "Formato de precio inválido"));
        }

        String standardizedSku;
        try {
            standardizedSku = skuValidationService.standardize(product.getSku());
        } catch (IllegalArgumentException e) {
            errors.add(new ValidationErrorDetail("sku", e.getMessage()));
            throw new ValidationException(errors);
        }

        if (!skuValidationService.isValid(standardizedSku)) {
            errors.add(new ValidationErrorDetail("sku", "Formato de SKU inválido"));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }



        return persistencePort.findBySku(standardizedSku)
                .next()
                .flatMap(existingProduct ->
                        Mono.<Product>error(new DuplicateSkuException(standardizedSku))
                )
                .switchIfEmpty(
                        Mono.defer(() -> {
                            product.setSku(standardizedSku);
                            return persistencePort.save(product);
                        })
                );

     }

    @Override
    public Mono<Product> updateProduct(Long id, Product product) {
        return persistencePort.update(id, product);
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return persistencePort.deleteById(id);
    }

    @Override
    public Mono<Product> toggleProductStatus(Long id) {
        return persistencePort.findById(id)
                .flatMap(product -> {
                    product.setActive(!product.isActive());
                    return persistencePort.update(id, product);
                });    }

    @Override
    public Flux<Product> getAllProducts() {
        return persistencePort.findAll();
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public Flux<Product> filterByBrand(String brandName) {
        return persistencePort.findByBrand(brandName);
    }

    @Override
    public Flux<Product> filterByCategory(String categoryName) {
        return persistencePort.findByCategory(categoryName);
    }

    @Override
    public Flux<Product> filterByFeature(String featureName, String value) {
        return persistencePort.findByFeature(featureName, value);
    }

    @Override
    public Flux<Product> filterBySku(String sku) {
        return persistencePort.findBySku(sku)
                .switchIfEmpty(Flux.error(new SkuNotFoundException(sku)));

     }

    @Override
    public Flux<Product> filterByPriceRange(Double min, Double max) {
        return persistencePort.findByPriceRange(min, max);
    }

    @Override
    public Flux<Product> filterProducts(String brand, String category, String feature, String value, String sku, Double min, Double max) {
        return persistencePort.findAll()
                .filter(product -> brand == null || product.getBrand().getNombre().equalsIgnoreCase(brand))
                .filter(product -> category == null || product.getCategories().stream()
                        .anyMatch(c -> c.getName().equalsIgnoreCase(category)))
                .filter(product -> feature == null || value == null || product.getFeatures().stream()
                        .anyMatch(f -> {
                            return f.getValue().equalsIgnoreCase(value);
                        }))
                .filter(product -> sku == null || product.getSku().equalsIgnoreCase(sku))
                .filter(product -> min == null || max == null ||
                        (product.getUnitPrice().doubleValue() >= min && product.getUnitPrice().doubleValue() <= max));    }


}
