package com.bigtree.orders.repository;

import com.bigtree.orders.model.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface InventoryRepository extends CrudRepository<Inventory,Integer> {

    /**
     * Retrieve <code>Inventory</code>s from the data store for given productId
     * @return a <code>Inventory</code>
     */
    @Transactional(readOnly = true)
    Inventory findByProductId(String productId);
}
