package com.bigtree.orders.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InventoryResponse {

    private String basketId;
    private List<InventoryItem> items;
}
