package com.bigtree.orders.model;

import com.bigtree.orders.model.enums.Currency;
import com.bigtree.orders.model.enums.OrderStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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
	private Currency currency;

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
