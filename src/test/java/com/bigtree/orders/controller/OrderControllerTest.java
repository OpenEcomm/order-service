package com.bigtree.orders.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bigtree.orders.model.Address;
import com.bigtree.orders.model.Order;
import com.bigtree.orders.model.OrderItem;
import com.bigtree.orders.model.enums.OrderStatus;
import com.bigtree.orders.repository.OrderRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @BeforeAll
    public static void setup() {
        // mockMvc = MockMvcBuilders.standaloneSetup(new OrderController()).build();
    }

    @BeforeEach
    public void beforeEach() {
        Order order = Order.builder()
                .address(Address.builder().lineNumber1("lineNumber1").lineNumber2("lineNumber2").city("city")
                        .country("GB").postCode("ABC1234").build())
                .date(LocalDate.now())
                .email("user@gmail.com")
                .reference("reference")
                .status(OrderStatus.CREATED)
                .subTotal(BigDecimal.valueOf(20))
                .saleTax(BigDecimal.valueOf(2))
                .shippingCost(BigDecimal.valueOf(3))
                .totalCost(BigDecimal.valueOf(25))
                .build();
        order.setId(1);
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setPrice(BigDecimal.TEN);
        item.setProductId("123456");
        item.setProductName("Apple");
        item.setQuantity(2);
        item.setTotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        order.setItems(Sets.newHashSet(item));
        given(this.orderRepository.findAll()).willReturn(Lists.newArrayList(order));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        ResultActions actions = mockMvc.perform(get("/orders").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orders[0].id").value(1));
    }
}
