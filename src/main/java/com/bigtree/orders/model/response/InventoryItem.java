package com.bigtree.orders.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryItem {

    private String productId;
    private Integer requested;
    private Integer available;
    private Integer reserved;
}
