package com.bigtree.orders.repository;

import com.bigtree.orders.model.OrderReturn;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReturnRepository extends CrudRepository<OrderReturn,Integer>{
    
}
