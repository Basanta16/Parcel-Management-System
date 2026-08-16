package com.example.parcelmanagement.repository;

import com.example.parcelmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByName(String name);

//    @Query("SELECT c.serialNumber FROM Customer c ORDER BY c.serialNumber")
//    List<Integer> findAllUsedSerialNumbers();

    long count();

//    void deleteBySerialNumber(Integer serialNumber);
}
