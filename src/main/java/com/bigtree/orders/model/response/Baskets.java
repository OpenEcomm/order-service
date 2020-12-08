package com.bigtree.orders.model.response;

import java.util.List;

import com.bigtree.orders.model.Basket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Baskets {
    
    private List<Basket> baskets;
}
