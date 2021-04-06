package com.bigtree.orders.service;

import com.bigtree.orders.model.Basket;
import com.bigtree.orders.model.Inventory;
import com.bigtree.orders.model.request.UpdateInventoryRequest;
import com.bigtree.orders.model.response.InventoryItem;
import com.bigtree.orders.model.response.InventoryResponse;
import com.bigtree.orders.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public InventoryResponse check(final Basket basket) {
        log.info("Checking inventory for basket {}", basket.getBasketId());
        final List<InventoryItem> inventoryItems = new ArrayList<>();
        final InventoryResponse response = InventoryResponse.builder()
                .basketId(basket.getBasketId())
                .items(inventoryItems)
                .build();
        if (basket != null && !CollectionUtils.isEmpty(basket.getItems())) {
            basket.getItems().forEach(i -> {
                final Inventory inventory = inventoryRepository.findByProductId(i.getProductId());
                if (inventory != null && inventory.getAvailable() > i.getQuantity()) {
                    log.error("Found Inventory record for product {} : Available: {}, Reserved: {}", i.getProductId(), inventory.getAvailable(), inventory.getReserved());
                    final InventoryItem inventoryItem = InventoryItem.builder()
                            .available(inventory.getAvailable())
                            .requested(i.getQuantity())
                            .productId(i.getProductId())
                            .reserved(inventory.getReserved())
                            .build();
                    inventoryItems.add(inventoryItem);
                }else{
                    log.error("No inventory record available for product {}", i.getProductId());
                    final InventoryItem inventoryItem = InventoryItem.builder()
                            .available(0)
                            .reserved(0)
                            .requested(i.getQuantity())
                            .productId(i.getProductId())
                            .build();
                    inventoryItems.add(inventoryItem);
                }
            });
        }
        return response;
    }

    public void update(UpdateInventoryRequest request) {
        log.info("Updating inventory for product {}", request.getProductId());
        Inventory inventory = inventoryRepository.findByProductId(request.getProductId());
        if (inventory != null) {
            Integer available = inventory.getAvailable();
            if (request.getAdd() > 0) {
                available = available + request.getAdd();
            }
            if (request.getTake() > 0) {
                available = available - request.getTake();
            }
            Integer reserved = inventory.getReserved();
            if (request.getReserve() > 0) {
                reserved = reserved + request.getReserve();
            }
            inventory.setAvailable(available);
            inventory.setReserved(reserved);
            inventoryRepository.save(inventory);
        }else{
            inventory = Inventory.builder()
                    .productId(request.getProductId())
                    .productName(request.getProductName())
                    .available(request.getAdd())
                    .reserved(request.getReserve())
                    .build();
            inventoryRepository.save(inventory);
        }
        log.info("Inventory updated for product {}", request.getProductId());
    }

    public List<Inventory> listAll() {
        log.info("Retrieving all inventory");
        List<Inventory> objects = new ArrayList<>();
        Iterable<Inventory> all = inventoryRepository.findAll();
        for (Inventory inventory : all) {
            objects.add(inventory);
        }
        return objects;

    }

    public Inventory get(String productId) {
        log.info("Retrieving inventory for productId: {}", productId);
        return inventoryRepository.findByProductId(productId);
    }
}
