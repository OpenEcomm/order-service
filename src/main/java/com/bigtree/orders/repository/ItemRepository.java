package com.bigtree.orders.repository;

import com.bigtree.orders.model.OrderItem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<OrderItem,Integer>{
    
}
