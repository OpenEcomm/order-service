package com.bigtree.orders.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.bigtree.orders.model.Order;
import com.bigtree.orders.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional(readOnly = true)
    public void shouldFindOrderByEmail() {
        List<Order> orders = orderRepository.findByEmail("nava.arul@gmail.com");
        // assertThat(orders).(1);

        orders = orderRepository.findByEmail("nava.arul@nodomain.com");
        assertThat(orders).isEmpty();

    }
}
