package com.bigtree.orders.model;

import com.bigtree.orders.model.enums.Currency;
import com.bigtree.orders.model.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;

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

	@Column(name = "cancellation_requested", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
	private boolean cancellationRequested;

	@Column(name = "cancellation_approved", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
	private boolean cancellationApproved;

	@Column(name = "cancellation_declined", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
	private boolean cancellationDeclined;

	@Column(name = "cancelled", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
	private boolean cancelled;

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

	@Override
	public String toString() {
		return "Order{" +
				"date=" + date +
				", items=" + items +
				", email='" + email + '\'' +
				", reference='" + reference + '\'' +
				", currency='" + currency + '\'' +
				", shippingCost=" + shippingCost +
				", saleTax=" + saleTax +
				", subTotal=" + subTotal +
				", totalCost=" + totalCost +
				", status=" + status +
				", cancellationRequested=" + cancellationRequested +
				", cancellationApproved=" + cancellationApproved +
				", cancellationDeclined=" + cancellationDeclined +
				", cancelled=" + cancelled +
				", address=" + address +
				", expectedDeliveryDate=" + expectedDeliveryDate +
				'}';
	}
}
