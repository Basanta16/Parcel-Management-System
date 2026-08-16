package com.example.parcelmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="customer_parcel")
@Getter
@Setter
public class CustomerParcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime acceptedDate;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    private int serialNumber;

    @ManyToOne
    private ParcelType parcelType;


}
