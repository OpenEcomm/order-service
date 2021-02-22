package com.bigtree.orders.model.util;

import java.util.List;
import java.util.stream.Collectors;

import com.bigtree.orders.model.Basket;
import com.bigtree.orders.model.BasketItem;
import com.bigtree.orders.model.Order;
import com.bigtree.orders.model.OrderItem;

import org.springframework.util.CollectionUtils;

public class BasketToOrder {

    public static Order fromBasket(Basket basket) {
        if (basket != null) {
            return Order.builder()
            .email(basket.getEmail())
            .build();
        }
        return null;
    }

    public static OrderItem fromBasketItem(BasketItem basketItem) {
        if (basketItem != null) {
            return OrderItem.builder()
            .price(basketItem.getPrice())
            .productId(basketItem.getProductId())
            .productName(basketItem.getProductName())
            .quantity(basketItem.getQuantity())
            .total(basketItem.getTotal())
            .build();
        }
        return null;
    }

    public static List<OrderItem> fromBasketItems(List<BasketItem> basketItems) {
        if (!CollectionUtils.isEmpty(basketItems) ) {
            return basketItems.stream().map(BasketToOrder::fromBasketItem).collect(Collectors.toList());
        }
        return null;
    }
}
