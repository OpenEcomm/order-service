package com.bigtree.orders.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureDataJpa
public class OrderRepositoryTest {
    
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Description("Should return total number of orders for the given date")
    public void testCountByDate(){
        LocalDate today = LocalDate.now();
        // LocalDate today = LocalDate.of(2020, 12, 2);
        Long count = orderRepository.countByDate(today);
        Assertions.assertNotNull(count);
        // Assertions.assertThat(count).isGreaterThan(0);
        // Assertions.assertNotNull()
    }
}
