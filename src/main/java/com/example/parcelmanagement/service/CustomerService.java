package com.example.parcelmanagement.service;


import com.example.parcelmanagement.pojo.CustomerPojo;
import com.example.parcelmanagement.pojo.CustomerResponseProjection;

import java.util.List;

public interface CustomerService {

    public Integer createCustomerDetails(CustomerPojo customerPojo);
    public List<CustomerResponseProjection> getAllCustomerDetailsByParcelType(String parcelTypeName);
    public String markAsPickedUp(String parcelTypeName,
                                 Integer serialNumber);

}
