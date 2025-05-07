package com.arka.supplier_service.infraestructure.adapters.driver.rest.mapper;

import com.arka.supplier_service.domain.model.SupplierReceipt;
import com.arka.supplier_service.domain.model.SupplierReceiptDetail;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierReceiptDetailResponseDto;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierReceiptRequestDto;
import com.arka.supplier_service.infraestructure.adapters.driver.rest.dto.SupplierReceiptResponseDto;

import java.util.stream.Collectors;

public class SupplierReceiptRestMapper {

    public static SupplierReceipt toDomain(SupplierReceiptRequestDto dto) {
        return SupplierReceipt.builder()
                .id(null)
                .supplierOrderId(dto.getSupplierOrderId())
                .receivedBy(dto.getReceivedBy())
                .notes(dto.getNotes())
                .details(dto.getDetails().stream()
                        .map(detailDto -> SupplierReceiptDetail.builder()
                                .id(null)
                                .supplierReceiptId(null)
                                .sku(detailDto.getSku())
                                .quantityReceived(detailDto.getQuantityReceived())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static SupplierReceiptResponseDto toResponseDto(SupplierReceipt domain) {
        return SupplierReceiptResponseDto.builder()
                .id(domain.getId())
                .supplierOrderId(domain.getSupplierOrderId())
                .receivedBy(domain.getReceivedBy())
                .notes(domain.getNotes())
                .receivedDate(domain.getReceivedDate())
                .details(domain.getDetails().stream()
                        .map(detail -> SupplierReceiptDetailResponseDto.builder()
                                .id(detail.getId())
                                .sku(detail.getSku())
                                .quantityReceived(detail.getQuantityReceived())
                                .build())
                        .collect(Collectors.toList()))
                .build();
}
}