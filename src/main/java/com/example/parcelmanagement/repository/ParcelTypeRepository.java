package com.example.parcelmanagement.repository;

import com.example.parcelmanagement.entity.ParcelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParcelTypeRepository extends JpaRepository<ParcelType, Integer> {
    ParcelType findParcelTypeByParcelTypeName(String parcelTypeName);
}
