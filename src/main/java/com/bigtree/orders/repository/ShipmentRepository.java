package com.bigtree.orders.repository;

import com.bigtree.orders.model.Shipment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends CrudRepository<Shipment, Integer>{
    
}
