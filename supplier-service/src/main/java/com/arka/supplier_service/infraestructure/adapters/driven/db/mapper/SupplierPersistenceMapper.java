package com.arka.supplier_service.infraestructure.adapters.driven.db.mapper;

import com.arka.supplier_service.domain.model.Supplier;
import com.arka.supplier_service.infraestructure.adapters.driven.db.entity.SupplierEntity;

public class SupplierPersistenceMapper {
    public static SupplierEntity toEntity(Supplier supplier) {
        return new SupplierEntity(
                supplier.getId(),
                supplier.getName(),
                supplier.getNit(),
                supplier.getContactName(),
                supplier.getContactEmail(),
                supplier.getContactPhone(),
                supplier.getActive(),
                supplier.getCreatedAt()
        );
    }

    public static Supplier toDomain(SupplierEntity entity) {
        return new Supplier(
                entity.getId(),
                entity.getName(),
                entity.getNit(),
                entity.getContactName(),
                entity.getContactEmail(),
                entity.getContactPhone(),
                entity.getActive(),
                entity.getCreatedAt()
        );
    }
}
