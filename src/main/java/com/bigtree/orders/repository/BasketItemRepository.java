package com.bigtree.orders.repository;

import com.bigtree.orders.model.BasketItem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepository extends CrudRepository<BasketItem,Integer>{
    
}
