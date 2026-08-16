package com.example.parcelmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@RequiredArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Long count;
    private LocalDateTime acceptedDate;

    @ManyToOne
    @JoinColumn(name = "parcel_type_id")
    public ParcelType parcelType;

    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL)
    public List<CustomerParcel> customerParcels;

    public Customer(String name, ParcelType parcelType) {
        this.name = name;
        this.parcelType = parcelType;
    }
}
