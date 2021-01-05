package com.bigtree.orders.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.bigtree.orders.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

	@Column(name = "order_date")
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private LocalDate date;

	// @JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
	private Set<OrderItem> items;

	@Column(name = "email", nullable = false)
	@NotEmpty
	private String email;

	@Column(name = "reference", nullable = false)
	@NotEmpty
	private String reference;

	@Column(name = "currency", nullable = false)
	@NotEmpty
	private String currency;

	@Column(name = "shipping_cost", nullable = false)
	private BigDecimal shippingCost;

	@Column(name = "sale_tax", nullable = false)
	private BigDecimal saleTax;

	@Column(name = "sub_total", nullable = false)
	private BigDecimal subTotal;

	@Column(name = "total_cost", nullable = false)
	private BigDecimal totalCost;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Embedded
	@AttributeOverrides({ 
			@AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
			@AttributeOverride(name = "lastName", column = @Column(name = "last_name")),
			@AttributeOverride(name = "lineNumber1", column = @Column(name = "line_number1")),
			@AttributeOverride(name = "lineNumber2", column = @Column(name = "line_number2")),
			@AttributeOverride(name = "city", column = @Column(name = "city")),
			@AttributeOverride(name = "postCode", column = @Column(name = "postcode")),
			@AttributeOverride(name = "country", column = @Column(name = "country")),
			@AttributeOverride(name = "mobile", column = @Column(name = "mobile")),
			@AttributeOverride(name = "notes", column = @Column(name = "notes")),
		})
	private Address address;

	@Column(name = "expected_delivery_date")
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private LocalDate expectedDeliveryDate;

}
