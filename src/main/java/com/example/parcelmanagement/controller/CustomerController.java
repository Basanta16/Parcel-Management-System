package com.example.parcelmanagement.controller;


import com.example.parcelmanagement.pojo.CustomerPojo;
import com.example.parcelmanagement.pojo.CustomerResponseProjection;
import com.example.parcelmanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

       private final CustomerService customerService;


    @PostMapping("/save")
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerPojo customerPojo) {
        Integer serialNumber = customerService.createCustomerDetails(customerPojo);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerPojo.getName()
                + " is assigned to Serial No. "
                + serialNumber
                + " for "
                + customerPojo.getParcelType());
    }

    @GetMapping("/getAllCustomers/{parcelTypeName}")
    public ResponseEntity<List<CustomerResponseProjection>> getAllCustomers(@PathVariable("parcelTypeName") String parcelTypeName) {
        List<CustomerResponseProjection> allCustomerDetails = customerService.getAllCustomerDetailsByParcelType(parcelTypeName);
        return ResponseEntity.status(HttpStatus.OK).body(allCustomerDetails);
    }

    @DeleteMapping("/pickedUp/{parcelTypeName}/{serialNumber}")
    public ResponseEntity<String> pickedUp(@PathVariable("parcelTypeName") String parcelTypeName,
                                           @PathVariable("serialNumber") Integer serialNumber) {
        String result = customerService.markAsPickedUp(parcelTypeName, serialNumber);
        return ResponseEntity.ok(result);
    }
}
