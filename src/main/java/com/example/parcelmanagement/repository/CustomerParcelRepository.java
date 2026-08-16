package com.example.parcelmanagement.repository;

import com.example.parcelmanagement.entity.CustomerParcel;
import com.example.parcelmanagement.pojo.CustomerResponseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerParcelRepository extends JpaRepository<CustomerParcel, Integer> {


    Optional<CustomerParcel> findCustomerParcelByCustomer_Id(int customerId);

    @Query("SELECT c.serialNumber FROM CustomerParcel c ORDER BY c.serialNumber")
    List<Integer> findAllUsedSerialNumbers();
    Optional<CustomerParcel> findFirstByCustomer_IdAndParcelType_Id(int customerId, int parcelTypeId);

    @Query(
            value = """
            SELECT CASE
                WHEN COUNT(*) > 0 THEN TRUE
                ELSE FALSE
            END
            FROM customer_parcel
            WHERE parcel_type_id = :parcelTypeId
            AND serial_number = :serialNumber
            """,
            nativeQuery = true
    )
    long existsByParcelTypeIdAndSerialNumber(
            @Param("parcelTypeId") int parcelTypeId,
            @Param("serialNumber") int serialNumber
    );

    @Query(
            value = """

                    SELECT
                                                 cp.serial_number as serialNumber,
                                                 c.name as customerName,
                                                 COUNT(cp.id) as count,
                                                 p.parcel_type_name as parcelTypeName,
                                                 MIN(cp.accepted_date) AS lastAcceptedDate
                                             FROM customers c
                                                      LEFT JOIN customer_parcel cp
                                                                ON c.id = cp.customer_id
                                                      LEFT JOIN parceltype p
                                                                ON cp.parcel_type_id = p.id
                                             WHERE p.parcel_type_name = :parcelTypeName
                                             GROUP BY
                                                 c.id,
                                                 c.name,
                                                 cp.serial_number,
                                                 p.parcel_type_name
                                             ORDER BY cp.serial_number
            """,
            nativeQuery = true
    )
        List<CustomerResponseProjection> findCustomerDetailsByParcelName(@Param("parcelTypeName") String parcelTypeName);

    List<CustomerParcel>
    findAllByParcelType_IdAndSerialNumber(
            Integer parcelTypeId,
            Integer serialNumber
    );

    boolean existsByCustomer_Id(
            Integer customerId
    );
}
