package com.bigtree.orders.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.bigtree.orders.model.Address;
import com.bigtree.orders.model.Basket;
import com.bigtree.orders.model.BasketItem;
import com.bigtree.orders.repository.BasketRepository;
import com.bigtree.orders.service.BasketService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BasketControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BasketService basketService;

    @MockBean
    BasketRepository basketRepository;

    @BeforeEach
    public void init() {
        Basket basket = Basket.builder()
                .address(Address.builder().lineNumber1("lineNumber1").lineNumber2("lineNumber2").city("city")
                        .country("GB").postCode("ABC1234").build())
                .date(LocalDate.now()).email("user@gmail.com")
                .currency("GBP")
                .expectedDeliveryDate(LocalDate.now())
                .saleTax(BigDecimal.TEN)
                .shippingCost(BigDecimal.TEN)
                .subTotal(BigDecimal.valueOf(100))
                .totalCost(BigDecimal.TEN)
                .build();
        basket.setId(1);
        BasketItem item = new BasketItem();
        item.setBasket(basket);
        item.setPrice(BigDecimal.TEN);
        item.setProductId("123456");
        item.setProductName("Apple");
        item.setQuantity(10);
        item.setTotal(item.getPrice().multiply(BigDecimal.valueOf(10)));
        basket.setItems(Sets.newHashSet(item));
        given(this.basketRepository.findAll()).willReturn(Lists.newArrayList(basket));
    }

    @Test
    public void shouldCreateBasket() throws Exception {
        ResultActions actions = mockMvc.perform(get("/baskets").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.baskets[0].id").value(1));
    }
}
