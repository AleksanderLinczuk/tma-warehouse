package com.smartbusiness.tmawarehouse.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "employee_name")
    private String employeeName;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemEntity itemEntity;

    @Column(name = "unit_of_measurement")
    private UnitOfMeasurement unitOfMeasurement;

    private int quantity;

    @Column(name = "price_UAH")
    private double priceUAH;

    private String comment;


}
