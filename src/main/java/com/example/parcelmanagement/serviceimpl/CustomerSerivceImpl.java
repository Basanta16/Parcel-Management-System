package com.example.parcelmanagement.serviceimpl;

import com.example.parcelmanagement.entity.Customer;
import com.example.parcelmanagement.entity.CustomerParcel;
import com.example.parcelmanagement.entity.ParcelType;
import com.example.parcelmanagement.pojo.CustomerPojo;
import com.example.parcelmanagement.pojo.CustomerResponseProjection;
import com.example.parcelmanagement.repository.CustomerParcelRepository;
import com.example.parcelmanagement.repository.CustomerRepository;
import com.example.parcelmanagement.repository.ParcelTypeRepository;
import com.example.parcelmanagement.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerSerivceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerParcelRepository customerParcelRepository;
    private final ParcelTypeRepository parcelTypeRepository;

    private static final int MAX_SERIAL_NUMBER = 70;

    private Integer getNextAvailableSerialNumber(
            int parcelTypeId
    ) {

        for (int serialNumber = 1;
             serialNumber <= MAX_SERIAL_NUMBER;
             serialNumber++) {

            long exists = customerParcelRepository.existsByParcelTypeIdAndSerialNumber(parcelTypeId,serialNumber);

            if (exists == 0) {
                return serialNumber;
            }
        }

        throw new RuntimeException(
                "No serial number available for this courier"
        );
    }

    @Override
    public Integer createCustomerDetails(CustomerPojo customerPojo) {

        Optional<Customer> optExistedCustomer = customerRepository.findCustomerByName(customerPojo.getName());
        ParcelType parcelType = parcelTypeRepository.findParcelTypeByParcelTypeName(customerPojo.getParcelType());
        if (optExistedCustomer.isPresent()) {
            Customer existedCustomer = optExistedCustomer.get();
            existedCustomer.setAcceptedDate(LocalDateTime.now());
            Optional<CustomerParcel> existingCustomerParcel = customerParcelRepository.findFirstByCustomer_IdAndParcelType_Id(existedCustomer.getId(), parcelType.getId());
            CustomerParcel newCustomerParcel = new CustomerParcel();
            newCustomerParcel.setCustomer(existedCustomer);
            newCustomerParcel.setParcelType(parcelType);
            newCustomerParcel.setAcceptedDate(LocalDateTime.now());
            // Customer already has parcel from this courier
            if (existingCustomerParcel.isPresent()) {
                newCustomerParcel.setSerialNumber(existingCustomerParcel.get().getSerialNumber());
            }
            //Customer has new courier parcel
            else {
                newCustomerParcel.setSerialNumber(getNextAvailableSerialNumber(parcelType.getId()));
            }
            customerParcelRepository.save(newCustomerParcel);
            return newCustomerParcel.getSerialNumber();

        }
        else {

            Customer newCustomer = new Customer();
            newCustomer.setName(customerPojo.getName());
            newCustomer.setAcceptedDate(LocalDateTime.now());
            Customer savedCustomer = customerRepository.save(newCustomer);
            CustomerParcel newCustomerParcel = new CustomerParcel();
            newCustomerParcel.setCustomer(savedCustomer);
            newCustomerParcel.setParcelType(parcelType);
            newCustomerParcel.setSerialNumber(getNextAvailableSerialNumber(parcelType.getId()));
            newCustomerParcel.setAcceptedDate(LocalDateTime.now());
            customerParcelRepository.save(newCustomerParcel);
            return newCustomerParcel.getSerialNumber();

        }
        }

    @Override
    public List<CustomerResponseProjection> getAllCustomerDetailsByParcelType(String parcelTypeName) {
       return customerParcelRepository.findCustomerDetailsByParcelName(parcelTypeName);
    }

    @Override
    @Transactional
    public String markAsPickedUp(String parcelTypeName, Integer serialNumber) {
        ParcelType parcelType = parcelTypeRepository.findParcelTypeByParcelTypeName(parcelTypeName);
        if (parcelType == null) {
            return "Courier not found: " + parcelTypeName;
        }
        // 1. Find all parcels using this courier + serial number
        List<CustomerParcel> parcels =
                customerParcelRepository
                        .findAllByParcelType_IdAndSerialNumber(
                                parcelType.getId(),
                                serialNumber
                        );
        // 2. Nothing found
        if (parcels.isEmpty()) {

            return "No parcel found for serial "
                    + serialNumber;
        }

        // 3. Get customer before deleting parcels
        Customer customer =
                parcels.get(0).getCustomer();

        Integer customerId =
                customer.getId();

        String customerName =
                customer.getName();


        // 4. Delete all parcels under this serial number
        customerParcelRepository
                .deleteAll(parcels);


        // 5. Flush deletion immediately
        customerParcelRepository.flush();


        // 6. Check whether customer has parcels
        //    with another courier
        boolean customerStillHasParcels =
                customerParcelRepository
                        .existsByCustomer_Id(
                                customerId
                        );


        // 7. If customer has NO parcels left,
        //    delete customer completely
        if (!customerStillHasParcels) {

            customerRepository
                    .deleteById(customerId);
        }
        return "✅ "
                + customerName
                + "'s parcels were picked up. "
                + "Serial No. "
                + serialNumber
                + " is now FREE.";
    }

}
