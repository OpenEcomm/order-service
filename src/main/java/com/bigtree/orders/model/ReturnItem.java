package com.bigtree.orders.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "return_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnItem extends BaseEntity{
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_return_id", nullable = false )
    @JsonBackReference
    private OrderReturn orderReturn;
    
    @Column(name = "product_id", nullable = false)
    @NotEmpty
    private String productId;

    @Column(name = "product_name", nullable = false)
    @NotEmpty
    private String productName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

}
