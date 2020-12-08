package com.bigtree.orders.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.bigtree.orders.model.enums.ShipmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "shipments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipment extends BaseEntity{
    
    @Column(name="order_id", nullable = false)
    private int orderId;

    @Column(name="courier_id", nullable = false)
    private int courierId;

    @Column(name="tracking_reference", nullable = false)
    private int trackingReference;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;
    
}
