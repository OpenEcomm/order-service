package com.bigtree.orders.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "baskets")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Basket extends BaseEntity{

    @Column(name = "date")
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private LocalDate date;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "basket", fetch = FetchType.EAGER)
	private Set<BasketItem> items;

	@Column(name = "basket_id", nullable = false)
	private String basketId;

	@Column(name = "email", nullable = false)
	@NotEmpty
	private String email;

	@Column(name = "order_reference")
	private String orderReference;

	@Column(name = "total", nullable = false)
	private BigDecimal total;

	public void dismissChild(BasketItem child) {
		this.items.remove(child);
	}

	@PreRemove
	public void dismissChildren() {
		this.items.forEach(child -> child.dismissParent()); // SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
		this.items.clear();
	}
}
