package com.example.parcelmanagement.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parceltype")
@Getter
@Setter
public class ParcelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String parcelTypeName;
}
