package com.example.product_service.repository;

import com.example.product_service.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant,String> {
    boolean existsByTenantName(String tenantName);
}

