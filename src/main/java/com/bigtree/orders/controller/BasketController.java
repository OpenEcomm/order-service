package com.bigtree.orders.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bigtree.orders.model.Basket;
import com.bigtree.orders.model.enums.Action;
import com.bigtree.orders.model.response.ActionResponse;
import com.bigtree.orders.model.response.Baskets;
import com.bigtree.orders.repository.BasketRepository;
import com.bigtree.orders.service.BasketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BasketController {

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    BasketService basketService;

    @GetMapping("/baskets")
    public ResponseEntity<Baskets> baskets(@RequestParam(required = false) Map<String, String> qParams) {
        List<Basket> baskets = null;
        if (CollectionUtils.isEmpty(qParams)) {
            log.info("Received request to get all baskets");
            baskets = basketRepository.findAll();
        } else {
            log.info("Received request to get orders with query {}", qParams.toString());
            baskets = basketService.findOrdersWithQuery(qParams);
        }
        List<Basket> basketList = new ArrayList<>();
        if (baskets != null) {
            baskets.forEach(basketList::add);
        }
        return ResponseEntity.ok().body(Baskets.builder().baskets(baskets).build());
    }

    @GetMapping("/baskets/{id}")
    public ResponseEntity<Basket> getById(@PathVariable("id") Integer id) {
        log.info("Received request to get order by id {}", id);
        Optional<Basket> basketOptional = basketRepository.findById(id);
        return ResponseEntity.ok().body(basketOptional.isPresent() ? basketOptional.get() : null);
    }

    @PutMapping("/baskets/{id}")
    public ResponseEntity<ActionResponse> updateStatus(@RequestBody Basket basket, @PathVariable("id") Integer id) {
        log.info("Received request to update basket {}", id);
        boolean updated = basketService.updateBasket(id, basket);
        return ResponseEntity.ok()
                .body(ActionResponse.builder().action(Action.UPDATE).status(updated).object("Bakset").id(id).build());
    }

    @DeleteMapping("/baskets/{id}")
    public ResponseEntity<ActionResponse> delete(@PathVariable("id") Integer id) {
        log.info("Received request to delete order by id {}", id);
        boolean deleted = basketService.deleteById(id);
        return ResponseEntity.ok()
                .body(ActionResponse.builder().action(Action.DELETE).status(deleted).object("Order").id(id).build());
    }

    @PostMapping("/baskets")
    public ResponseEntity<Void> create(@RequestBody Basket basket) {
        log.info("Received request to create new basket. {}", basket);
        basketService.create(basket);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
