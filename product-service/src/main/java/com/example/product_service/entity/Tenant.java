package com.example.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

    @Id
    private String id;

    @Column(nullable = false,unique = true)
    private String tenantName;

    @Column(nullable = false)
    private String orgName;

    @Column(nullable = false)
    private String adminEmail;

    @Column(nullable = false)
    private String adminPassword;

    private String address;
    private String contactEmail;
    private String contactPhone;

    @Column(nullable = false)
    private Boolean active = true;


    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
