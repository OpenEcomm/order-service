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
public class PaymentIntentRequest {

    private long subTotal;
    private long saleTax;
    private long deliveryCost;
    private long packagingCost;
    private String currency;
}
