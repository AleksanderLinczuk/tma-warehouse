package com.smartbusiness.tmawarehouse.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")

public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_group", columnDefinition = "varchar")
    private ItemGroup itemGroup;


    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measurement", columnDefinition = "varchar")
    private UnitOfMeasurement unitOfMeasurement;

    private int quantity;

    @Column(name = "price_UAH")
    private double priceUAH;

    private String status;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "contact_person")
    private String contactPerson;


}
