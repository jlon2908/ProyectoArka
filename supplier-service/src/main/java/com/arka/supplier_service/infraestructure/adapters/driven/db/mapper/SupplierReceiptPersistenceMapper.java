package com.arka.supplier_service.infraestructure.adapters.driven.db.mapper;

import com.arka.supplier_service.domain.model.SupplierReceipt;
import com.arka.supplier_service.domain.model.SupplierReceiptDetail;
import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierReceiptDetailEntity;
import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierReceiptEntity;

import java.util.List;

public class SupplierReceiptPersistenceMapper {

    public static SupplierReceiptEntity toEntity(SupplierReceipt receipt) {
        return SupplierReceiptEntity.builder()
                .id(receipt.getId())
                .supplierOrderId(receipt.getSupplierOrderId())
                .receivedBy(receipt.getReceivedBy())
                .notes(receipt.getNotes())
                .createdAt(receipt.getReceivedDate()) // Ojo: en el dominio es receivedDate
                .build();
    }

    public static SupplierReceipt toDomain(SupplierReceiptEntity entity, List<SupplierReceiptDetail> details) {
        return SupplierReceipt.builder()
                .id(entity.getId())
                .supplierOrderId(entity.getSupplierOrderId())
                .receivedBy(entity.getReceivedBy())
                .notes(entity.getNotes())
                .receivedDate(entity.getCreatedAt()) // Mapeamos a receivedDate en el dominio
                .details(details)
                .build();
    }

    public static SupplierReceiptDetailEntity toDetailEntity(SupplierReceiptDetail detail) {
        return SupplierReceiptDetailEntity.builder()
                .id(detail.getId())
                .supplierReceiptId(detail.getSupplierReceiptId())
                .sku(detail.getSku())
                .quantityReceived(detail.getQuantityReceived())
                .build();
    }

    public static SupplierReceiptDetail toDetailDomain(SupplierReceiptDetailEntity entity) {
        return SupplierReceiptDetail.builder()
                .id(entity.getId())
                .supplierReceiptId(entity.getSupplierReceiptId())
                .sku(entity.getSku())
                .quantityReceived(entity.getQuantityReceived())
                .build();
    }
}