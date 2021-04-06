package com.bigtree.orders.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateInventoryRequest {

    private String productId;
    private String productName;
    private int reserve;
    private int add;
    private int take;
}
