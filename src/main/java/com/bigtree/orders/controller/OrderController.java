package com.bigtree.orders.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bigtree.orders.model.Order;
import com.bigtree.orders.model.enums.Action;
import com.bigtree.orders.model.request.UpdateStatus;
import com.bigtree.orders.model.response.ActionResponse;
import com.bigtree.orders.model.response.OrderCreateResponse;
import com.bigtree.orders.model.response.Orders;
import com.bigtree.orders.repository.OrderRepository;
import com.bigtree.orders.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @CrossOrigin(origins = "*")
    @GetMapping("/orders")
    public ResponseEntity<Orders> orders(@RequestParam(required = false) Map<String, String> qparams) {
        List<Order> orders = null;
        if (CollectionUtils.isEmpty(qparams)) {
            log.info("Received request to get all orders");
            orders = orderRepository.findAll();
        } else {
            log.info("Received request to get ordered with query {}", qparams.toString());
            orders = orderService.findOrdersWithQuery(qparams);
        }
        List<Order> orderList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orders)) {
            orders.forEach(orderList::add);
        }
        return ResponseEntity.ok().body(Orders.builder().orders(orders).build());
    }

    @CrossOrigin(origins = "http://localhost")
    @GetMapping("/orders/search")
    public ResponseEntity<Orders> findAllMatch(@RequestParam(required = false) Map<String, String> qparams) {
        List<Order> orders = null;
        if (CollectionUtils.isEmpty(qparams)) {
            log.info("Received request to get all orders");
            orders = orderRepository.findAll();
        } else {
            log.info("Received request to get orders with query {}", qparams.toString());
            orders = orderService.findOrdersWithQuery(qparams);
        }
        List<Order> orderList = new ArrayList<>();
        if (orders != null) {
            orders.forEach(orderList::add);
        }
        return ResponseEntity.ok().body(Orders.builder().orders(orders).build());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getById(@PathVariable("id") Integer id) {
        log.info("Received request to get order by id {}", id);
        Optional<Order> order = orderRepository.findById(id);
        return ResponseEntity.ok().body(order.isPresent() ? order.get() : null);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<ActionResponse> updateStatus(@RequestBody UpdateStatus status,
            @PathVariable("id") Integer id) {
        log.info("Received request to update order by id {}", id);
        boolean updated = orderService.updateStatus(id, status);
        return ResponseEntity.ok()
                .body(ActionResponse.builder().action(Action.UPDATE).status(updated).object("Order").id(id).build());
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ActionResponse> delete(@PathVariable("id") Integer id) {
        log.info("Received request to delete order by id {}", id);
        boolean deleted = orderService.delete(id);
        return ResponseEntity.ok()
                .body(ActionResponse.builder().action(Action.DELETE).status(deleted).object("Order").id(id).build());
    }

    @CrossOrigin(origins = "http://localhost")
    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponse> create(@RequestBody Order order) {
        log.info("Received request to create new order. {}", order);
        String reference = orderService.create(order);
        if (!StringUtils.isEmpty(reference)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    OrderCreateResponse.builder().reference(reference).message("Order created successfully").build());
        } else {
            return ResponseEntity.status(HttpStatus.PROCESSING).body(
                OrderCreateResponse.builder().message("Order creation in progress").build());
        }
    }
}
