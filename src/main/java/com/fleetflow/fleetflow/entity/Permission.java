package com.fleetflow.fleetflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_vendor_id")
    private Vendor superVendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_vendor_id")
    private Vendor subVendor;
}
