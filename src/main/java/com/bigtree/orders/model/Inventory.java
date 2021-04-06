package com.bigtree.orders.model;


import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Table(name = "inventory")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseEntity{

    @Column(name = "product_id", nullable = false)
    @NotEmpty
    private String productId;

    @Column(name = "product_name", nullable = false)
    @NotEmpty
    private String productName;

    @Column(name = "available")
    private Integer available;

    @Column(name = "reserved")
    private Integer reserved;

}
