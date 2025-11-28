package com.example.product_service.service;



import com.example.product_service.repository.TenantRepository;
import com.example.product_service.entity.Tenant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public TenantService(TenantRepository tenantRepository){
        this.tenantRepository = tenantRepository;
    }

    public Tenant createTenant(Tenant tenant){
        //Tenant UUID
        String tenantId = UUID.randomUUID().toString();

        String schema = tenant.getOrgName().toLowerCase().replace(" ","")+"_"+tenantId.substring(0,8);

        //saved tenant data in master in DB
        Tenant tenants = new Tenant();
        tenants.setId(tenantId);
        tenants.setOrgName(tenant.getOrgName());
        tenants.setTenantName(schema);
        tenants.setAdminEmail(tenant.getAdminEmail());
        tenants.setAdminPassword(encoder.encode(tenant.getAdminPassword()));
        tenants.setAddress(tenant.getAddress());
        tenants.setContactPhone(tenant.getContactPhone());
        tenants.setContactEmail(tenant.getContactEmail());
        Tenant saved =  tenantRepository.save(tenants);


        return saved;
    }


}

