package com.bigtree.orders.repository;

import com.bigtree.orders.model.ReturnItem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnItemRepository extends CrudRepository<ReturnItem,Integer>{
    
}
