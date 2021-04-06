package com.bigtree.orders.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.bigtree.orders.model.Basket;
import com.bigtree.orders.model.BasketItem;
import com.bigtree.orders.repository.BasketItemRepository;
import com.bigtree.orders.repository.BasketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class BasketService {

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    BasketItemRepository basketItemRepository;

    public void create(Basket basket) {
        final Set<BasketItem> items = new HashSet<>(basket.getItems());
        log.info("Creating new basket with {} items for user: {}, Id: {}", items.size(), basket.getEmail(), basket.getBasketId());
        basket.setItems(null);
        if (basket.getDate() == null) {
            basket.setDate(LocalDate.now());
        }
        Basket saved = basketRepository.save(basket);
        if (saved != null) {
            log.info("Basket created for user: {}, Id: {}", saved.getEmail(), basket.getBasketId());
            for (BasketItem basketItem : items) {
                basketItem.setBasket(saved);
            }
            basketItemRepository.saveAll(items);
        } else {
            log.error("Basket not saved for user {}", basket.getEmail());
        }
    }

    public boolean delete(String email, String basketId) {
        log.info("Deleting a basket for user {} Id {}", email, basketId);
        Basket basket = basketRepository.findByEmailAndBasketId(email, basketId);
        if (basket != null) {
            basketRepository.delete(basket);
            return true;
        }
        return false;
    }

    public Basket retrieveBasket(String email, String basketId) {
        log.info("Retrieving a basket for user {} Id {}", email, basketId);
        return basketRepository.findByEmailAndBasketId(email, basketId);
    }

    public boolean deleteById(String basketId) {
        Optional<Basket> byBasketId = basketRepository.findByBasketId(basketId);
        if ( byBasketId.isPresent()){
            basketRepository.delete(byBasketId.get());
            return true;
        }
        return false;
    }

    public List<Basket> findBaskets(Map<String, String> qParams) {
        final List<Basket> result = new ArrayList<>();
        qParams.forEach((k, v) -> {
            if (k.equalsIgnoreCase("email")) {
                log.info("Looking for basket with email {}", v);
                result.add(basketRepository.findByEmail(v));
            } else if (k.equalsIgnoreCase("id")) {
                log.info("Looking for basket with id {}", v);
                Optional<Basket> findById = basketRepository.findById(Integer.parseInt(v));
                if (findById.isPresent()) {
                    result.add(findById.get());
                }
            } else if (k.equalsIgnoreCase("basketId")) {
                log.info("Looking for basket with BasketId {}", v);
                Optional<Basket> findById = basketRepository.findByBasketId(v);
                if (findById.isPresent()) {
                    result.add(findById.get());
                }
            }

        });
        return result;
    }

    public boolean updateBasket(String basketId, Basket basket, boolean createIfNew) {
        Optional<Basket> byBasketId = basketRepository.findByBasketId(basketId);
        if (byBasketId.isPresent()) {
            log.info("Found a basket with id {}", basketId);
            Basket record = byBasketId.get();
            record.setOrderReference(basket.getOrderReference());
            record.setTotal(basket.getTotal());
            Basket updatedBasket = basketRepository.save(record);
            Set<BasketItem> items = basket.getItems();

            List<BasketItem> updateList = new ArrayList<>();
            List<BasketItem> orphans = new ArrayList<>();
            // Update existing items and delete orphan
            for (BasketItem recordItem : updatedBasket.getItems()) {
                boolean found = false;
                for (BasketItem item : items) {
                    if ( item.getProductId().equalsIgnoreCase(recordItem.getProductId())){
                        recordItem.setPrice(item.getPrice());
                        recordItem.setQuantity(item.getQuantity());
                        recordItem.setTotal(item.getTotal());
                        recordItem.setProductName(item.getProductName());
                        updateList.add(recordItem);
                        found = true;
                        break;
                    }
                }
                // Delete Orphan
                if (! found){
                    orphans.add(recordItem);
                }
            }

            if (!CollectionUtils.isEmpty(updateList)){
                for (BasketItem item : updateList) {
                    log.info("Updating item {}", item.getProductId());
                    basketItemRepository.save(item);
                }
            }

            if (!CollectionUtils.isEmpty(orphans)){
                for (BasketItem orphan : orphans) {
                    log.info("Deleting orphan {}", orphan.getProductId());
                    basketItemRepository.delete(orphan);
                }
            }

            final Basket saved = basketRepository.save(record);
            // Save new items
            for (BasketItem item : items) {
                boolean found = false;
                for (BasketItem recordItem : saved.getItems()) {
                    if ( item.getProductId().equalsIgnoreCase(recordItem.getProductId())){
                        found = true;
                        break;
                    }
                }
                // Delete Orphan
                if (! found){
                    item.setBasket(saved);
                    log.info("Saving new item {} to basket {}", item.getProductId(), item.getBasket().getBasketId());
                    basketItemRepository.save(item);
                }
            }
            basketRepository.save(record);
            log.info("Basket is updated");
            return true;
        } else {
            log.info("No basket found with id {}", basketId);
            if ( createIfNew){
                basket.setBasketId(basketId);
                create(basket);
                return true;
            }
        }
        return false;
    }

}
