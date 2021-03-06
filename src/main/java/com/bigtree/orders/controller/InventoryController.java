package com.bigtree.orders.controller;

import com.bigtree.orders.model.Basket;
import com.bigtree.orders.model.Inventory;
import com.bigtree.orders.model.request.UpdateInventoryRequest;
import com.bigtree.orders.model.response.InventoryResponse;
import com.bigtree.orders.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@Slf4j
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/inventory/check")
    public ResponseEntity<InventoryResponse> check(@RequestBody Basket basket) {
        log.info("Received request to check inventory for basket {}", basket);
        InventoryResponse response = inventoryService.check(basket);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/inventory/update")
    public ResponseEntity<Void> update(@RequestBody UpdateInventoryRequest updateInventoryRequest) {
        log.info("Received request to update inventory {}", updateInventoryRequest);
        inventoryService.update(updateInventoryRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("inventory")
    public ResponseEntity<List<Inventory>> list() {
        log.info("Received request to get all inventory");
        List<Inventory> all = inventoryService.listAll();
        log.info("Returning {} inventories", all.size());
        return ResponseEntity.ok(all);
    }

    @GetMapping("inventory/{productId}")
    public ResponseEntity<Inventory> getByProductId(@PathVariable("productId") String productId) {
        log.info("Received request to get inventory for product {}", productId);
        final Inventory inventory = inventoryService.get(productId);
        if ( inventory != null){
            log.info("Returning inventory {} ", inventory);
        }else{
            log.info("Returning no inventory for {} ", productId);
        }
        return ResponseEntity.ok(inventory);
    }
}
