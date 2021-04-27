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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem extends BaseEntity{
    
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false )
    private Order order;

    @Column(name = "image", nullable = false, columnDefinition = "varchar(255) default '/assets/icons/product-image.png'")
    @NotEmpty
    private String image;

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
    @Column(name = "cancellation_requested", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
    private boolean cancellationRequested;

    @Column(name = "cancellation_approved", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
    private boolean cancellationApproved;

    @Column(name = "cancellation_declined", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
    private boolean cancellationDeclined;

    @Column(name = "cancelled", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
    private boolean cancelled;

    @Override
    public String toString() {
        return "OrderItem{" +
                "order=" + order +
                ", image='" + image + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", total=" + total +
                ", cancellationRequested=" + cancellationRequested +
                ", cancellationApproved=" + cancellationApproved +
                ", cancellationDeclined=" + cancellationDeclined +
                ", cancelled=" + cancelled +
                '}';
    }
}
