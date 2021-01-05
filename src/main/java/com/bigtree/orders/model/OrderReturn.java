package com.bigtree.orders.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bigtree.orders.model.enums.ReturnStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "returns")
@Entity
@EqualsAndHashCode(callSuper=false)
public class OrderReturn extends BaseEntity {
    
    @Column(name="order_id", nullable = false)
    private int orderId;

    @JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderReturn", fetch = FetchType.EAGER)
    private Set<ReturnItem> returnItems;
    
    @Enumerated(EnumType.ORDINAL)
    private ReturnStatus returnStatus;
}
