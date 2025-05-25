/*package com.fleetflow.fleetflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cab_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cab cab;

    private String licenseNumber;

    private String rcNumber;

    private Date licenseExpiry;

    private Date rcExpiry;
}
*/
package com.fleetflow.fleetflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cab_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cab cab;

    private String licenseNumber;

    private String rcNumber;

    private Date licenseExpiry;

    private Date rcExpiry;

    @Column(nullable = false)
    private boolean available = true;
}
