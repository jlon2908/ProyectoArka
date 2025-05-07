package com.arka.supplier_service.application.usecase;

import com.arka.supplier_service.application.ports.input.SupplierServicePort;
import com.arka.supplier_service.application.ports.output.SupplierPersistencePort;
import com.arka.supplier_service.domain.exception.supplier.SupplierNotFoundByNameException;
import com.arka.supplier_service.domain.exception.supplier.SupplierNotFoundException;
import com.arka.supplier_service.domain.model.Supplier;
import com.arka.supplier_service.domain.service.EmailValidationService;
import com.arka.supplier_service.domain.service.PhoneValidationService;
import com.arka.supplier_service.infraestructure.config.ValidationErrorDetail;
import com.arka.supplier_service.infraestructure.config.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierServicePort {

    private final SupplierPersistencePort supplierPersistencePort;
    private final EmailValidationService emailValidationService;
    private final PhoneValidationService phoneValidationService;



    @Override
    public Mono<Supplier> create(Supplier supplier) {

        try {
            validateSupplier(supplier);
        } catch (ValidationException e) {
            return Mono.error(e);
        }

        if (supplier.getId() == null) {
            supplier.setId(UUID.randomUUID());
        }
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setActive(true);
        return supplierPersistencePort.save(supplier);
   }

    @Override
    public Mono<Supplier> update(UUID id, Supplier supplier) {
        try {
            validateSupplier(supplier);
        } catch (ValidationException e) {
            return Mono.error(e);
        }

        return supplierPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new SupplierNotFoundException(id.toString())))
                .flatMap(existing -> {
                    supplier.setId(id); // protege el ID
                    supplier.setCreatedAt(existing.getCreatedAt()); // conserva la fecha original
                    supplier.setActive(existing.getActive()); // conserva el estado activo
                    return supplierPersistencePort.update(id, supplier);
                });
      }

    @Override
    public Mono<Supplier> findById(UUID id) {

        return supplierPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new SupplierNotFoundException(id.toString())));    }

    @Override
    public Flux<Supplier> findAll() {
        return supplierPersistencePort.findAll();
    }

    @Override
    public Flux<Supplier> findByName(String name) {

        return supplierPersistencePort.findByName(name)
                .switchIfEmpty(Flux.error(new SupplierNotFoundByNameException(name)));    }

    @Override
    public Flux<Supplier> findActive() {
        return supplierPersistencePort.findActive();
    }

    @Override
    public Mono<Void> activate(UUID id) {

        return supplierPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new SupplierNotFoundException(id.toString())))
                .then(supplierPersistencePort.activate(id));    }

    @Override
    public Mono<Void> deactivate(UUID id) {
        return supplierPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new SupplierNotFoundException(id.toString())))
                .then(supplierPersistencePort.deactivate(id));    }

    @Override
    public Mono<Void> delete(UUID id) {
        return supplierPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new SupplierNotFoundException(id.toString())))
                .then(supplierPersistencePort.delete(id));    }


    private void validateSupplier(Supplier supplier) {
        List<ValidationErrorDetail> errors = new ArrayList<>();

        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            errors.add(new ValidationErrorDetail("name", "El nombre no puede ser vacío"));
        }

        if (!emailValidationService.isValidEmail(supplier.getContactEmail())) {
            errors.add(new ValidationErrorDetail("contactEmail", "Formato de correo electrónico inválido"));
        }

        if (!phoneValidationService.isValidPhone(supplier.getContactPhone())) {
            errors.add(new ValidationErrorDetail("contactPhone", "Formato de número telefónico inválido"));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

}
