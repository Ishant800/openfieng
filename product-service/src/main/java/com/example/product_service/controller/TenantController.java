package com.example.product_service.controller;

import com.example.product_service.entity.Tenant;
import com.example.product_service.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService service;
    public TenantController( TenantService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Tenant> createTenants(@RequestBody Tenant tenant){
        return ResponseEntity.ok(service.createTenant(tenant));
    }

}
