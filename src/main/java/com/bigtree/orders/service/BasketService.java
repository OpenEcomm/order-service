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

@Service
@Slf4j
public class BasketService {

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    BasketItemRepository basketItemRepository;

    public void create(Basket basket) {
        Set<BasketItem> items = new HashSet<>(basket.getItems());
        log.info("Saving basket with {} items for user {}", items.size(), basket.getEmail());
        basket.setItems(null);
        if (basket.getDate() == null) {
            basket.setDate(LocalDate.now());
        }
        Basket saved = basketRepository.save(basket);
        if (saved != null) {
            log.info("Basket created for user {}", saved.getEmail());
            for (BasketItem basketItem : items) {
                basketItem.setBasket(saved);
            }
            basketItemRepository.saveAll(items);
        } else {
            log.error("Basket not saved for user {}", basket.getEmail());
        }
    }

    public boolean delete(String email) {
        Basket basket = basketRepository.findByEmail(email);
        if (basket != null) {
            basketRepository.delete(basket);
            return true;
        }
        return false;
    }

    public boolean deleteById(Integer id) {
        basketRepository.deleteById(id);
        return true;
    }

    public List<Basket> findOrdersWithQuery(Map<String, String> qParams) {
        final List<Basket> result = new ArrayList<>();
        qParams.forEach((k, v) -> {
            if (k.equalsIgnoreCase("email")) {
                log.info("Looking for basket with email {}", v);
                result.add(basketRepository.findByEmail(v));
            } else if (k.equalsIgnoreCase("id")) {
                log.info("Looking for basket with id {}", v);
                Optional<Basket> findById = basketRepository.findById(Integer.parseInt(v));
                if ( findById.isPresent()){
                    result.add(findById.get());
                }
            }

        });
        return result;
    }

	public boolean updateBasket(Integer id, Basket basket) {
        Basket save = basketRepository.save(basket);
        if ( save != null){
            log.info("Basket is updated");
            return true;
        }
		return false;
	}

}
