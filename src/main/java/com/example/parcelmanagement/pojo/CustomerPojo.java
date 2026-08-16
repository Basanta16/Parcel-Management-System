package com.example.parcelmanagement.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class CustomerPojo {

    private String name;
    private LocalDateTime acceptedDate;
    private String parcelType;
    private List<LocalDateTime> acceptedDates;
    private int serialNumber;
    private Long parcelCount;
}
