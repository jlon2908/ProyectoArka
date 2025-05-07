package com.arka.supplier_service.application.usecase;

import com.arka.supplier_service.application.ports.input.SupplierReceiptServicePort;
import com.arka.supplier_service.application.ports.output.SupplierReceiptPersistencePort;
import com.arka.supplier_service.domain.exception.SupplierReceiptFilterNoMatchException;
import com.arka.supplier_service.domain.exception.SupplierReceiptNotFoundBySupplierNameException;
import com.arka.supplier_service.domain.exception.supplierOrderRecipt.DuplicateSupplierReceiptForOrderException;
import com.arka.supplier_service.domain.exception.supplierOrderRecipt.SupplierReceiptNotFoundByDateRangeException;
import com.arka.supplier_service.domain.exception.supplierOrderRecipt.SupplierReceiptNotFoundByOrderCodeException;
import com.arka.supplier_service.domain.exception.supplierOrderRecipt.SupplierReceiptNotFoundByReceivedByException;
import com.arka.supplier_service.domain.model.SupplierReceipt;
import com.arka.supplier_service.domain.service.ReceivedByValidationService;
import com.arka.supplier_service.infraestructure.config.ValidationErrorDetail;
import com.arka.supplier_service.infraestructure.config.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierReceiptServiceImpl implements SupplierReceiptServicePort {

    private final SupplierReceiptPersistencePort persistencePort;
    private final ReceivedByValidationService regexNameValidator;

    @Override
    public Mono<SupplierReceipt> create(SupplierReceipt receipt) {
        List<ValidationErrorDetail> errors = new ArrayList<>();
        if (receipt.getId() == null) {
            receipt.setId(UUID.randomUUID());
        }

        receipt.getDetails().forEach(detail -> {
            if (detail.getId() == null) {
                detail.setId(UUID.randomUUID());
            }
            detail.setSupplierReceiptId(receipt.getId());
        });

        if (!regexNameValidator.isValid(receipt.getReceivedBy())) {
            errors.add(new ValidationErrorDetail("receivedBy", "El nombre debe tener al menos 3 letras"));
        }
        if (!errors.isEmpty()) {
            return Mono.error(new ValidationException(errors));
        }



        if (receipt.getReceivedDate() == null) {
            receipt.setReceivedDate(LocalDateTime.now());
        }



        return persistencePort.save(receipt)
                .onErrorMap(throwable -> {
                    if (throwable.getMessage() != null && throwable.getMessage().contains("duplicate key value violates unique constraint \"supplierreceipt_supplierorderid_key\"")) {
                        return new DuplicateSupplierReceiptForOrderException(receipt.getSupplierOrderId().toString());
                    }
                    return throwable;
});    }

    @Override
    public Flux<SupplierReceipt> findBySupplierName(String name) {
        return persistencePort.findBySupplierName(name)
                .switchIfEmpty(Mono.error(new SupplierReceiptNotFoundBySupplierNameException("No se encontraron recibos para el proveedor: " + name)));
    }

    @Override
    public Flux<SupplierReceipt> findByOrderCode(String code) {
        return persistencePort.findByOrderCode(code)
                .switchIfEmpty(Mono.error(new SupplierReceiptNotFoundByOrderCodeException(code)));
    }

    @Override
    public Flux<SupplierReceipt> findByReceivedBy(String receivedBy) {
        return persistencePort.findByReceivedBy(receivedBy)
                .switchIfEmpty(Mono.error(new SupplierReceiptNotFoundByReceivedByException(receivedBy)));
    }

    @Override
    public Flux<SupplierReceipt> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        return null;
    }



    @Override
    public Flux<SupplierReceipt> filterReceipts(String supplierName, String orderCode, String receivedBy, LocalDateTime from, LocalDateTime to) {
        return persistencePort.filterReceipts(supplierName, orderCode, receivedBy, from, to)
                .switchIfEmpty(Mono.error(new SupplierReceiptFilterNoMatchException()));
    }
}
