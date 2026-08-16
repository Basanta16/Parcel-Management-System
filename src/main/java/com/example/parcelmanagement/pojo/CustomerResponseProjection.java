package com.example.parcelmanagement.pojo;

import java.time.LocalDateTime;

public interface CustomerResponseProjection {
    Integer getSerialNumber();

    String getCustomerName();

    Long getCount();

    String getParcelTypeName();

    LocalDateTime getLastAcceptedDate();
}
