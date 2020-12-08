package com.bigtree.orders;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.bigtree.orders.model.Address;
import com.bigtree.orders.model.Order;
import com.bigtree.orders.model.OrderItem;
import com.bigtree.orders.model.enums.OrderStatus;
import com.bigtree.orders.service.EmailService;
import com.google.common.collect.Sets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmailSenderTest {

    @Autowired
    EmailService emailService;
    private Order order;

    @BeforeEach
    public void beforeEach() {
        order = Order.builder()
                .address(Address.builder().lineNumber1("1234, Galston Avenue").lineNumber2("Newton Mearns").city("Glasgow")
                        .country("GB").postCode("G775SF").build())
                .date(LocalDate.now()).email("bigtree.it.services@gmail.com").reference("206-8383367-9036364").status(OrderStatus.CREATED)
                .build();
        order.setId(1);
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setPrice(BigDecimal.TEN);
        item.setProductId("123456");
        item.setProductName("Apple");
        item.setQuantity(10);
        item.setTotal(item.getPrice().multiply(BigDecimal.valueOf(10)));

        OrderItem orange = new OrderItem();
        orange.setOrder(order);
        orange.setPrice(BigDecimal.TEN);
        orange.setProductId("321");
        orange.setProductName("Orange");
        orange.setQuantity(10);
        orange.setTotal(orange.getPrice().multiply(BigDecimal.valueOf(10)));
        HashSet<OrderItem> items = Sets.newHashSet(item, orange);
        order.setItems(items);
    }

    @Test
    public void testSendEmail() {
        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("count", order.getItems().size());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        params.put("today", dateFormat.format(cal.getTime()));
        emailService.sendMail("bigtree.it.services@gmail.com", "OpenBasket.com", "order", params);
    }
}
