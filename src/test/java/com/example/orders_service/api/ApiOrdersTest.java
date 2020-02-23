package com.example.orders_service.api;

import com.example.orders_service.domain.Order;
import com.example.orders_service.domain.Status;
import com.example.orders_service.domain.repository.OrdersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiOrdersTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrdersRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        clearTable();
    }

    @Test
    void testGetAll() throws Exception {
        Order firstOrder = repository.save(Instant.now(), Status.IN_PROGRESS);
        Order secondOrder = repository.save(Instant.now(), Status.DELIVERED);
        mockMvc.perform(get("/orders"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].id").value(firstOrder.getId()))
                .andExpect(jsonPath("$.[0].created").isNotEmpty())
                .andExpect(jsonPath("$.[0].status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.[1].id").value(secondOrder.getId()))
                .andExpect(jsonPath("$.[1].created").isNotEmpty())
                .andExpect(jsonPath("$.[1].status").value("DELIVERED"));
    }

    @Test
    void testGetById() throws Exception {
        Order order = repository.save(Instant.now(), Status.IN_PROGRESS);
        mockMvc.perform(get("/orders/" + order.getId()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.created").isNotEmpty())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void testGetByStatus() throws Exception {
        Order inProgressOrder = repository.save(Instant.now(), Status.IN_PROGRESS);
        Order rescheduleOrder = repository.save(Instant.now(), Status.RESCHEDULE);

        mockMvc.perform(get("/orders/filter?status=RESCHEDULE"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.[0].id").value(rescheduleOrder.getId()))
                .andExpect(jsonPath("$.[0].created").isNotEmpty())
                .andExpect(jsonPath("$.[0].status").value("RESCHEDULE"))
                .andExpect(jsonPath("$.[1]").doesNotExist());
    }

    @Test
    void testPost() throws Exception {
        mockMvc.perform(post("/orders"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.created").isNotEmpty())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void testPut() throws Exception {
        Order inProgressOrder = repository.save(Instant.now(), Status.IN_PROGRESS);
        OrderStatusDto body = new OrderStatusDto(inProgressOrder.getId(), "RESCHEDULE");
        mockMvc.perform(put("/orders").contentType(MediaType.APPLICATION_JSON).content(toJson(body)))
                .andExpect(status().is(200));

        Optional<Order> updatedOrder = repository.findById(inProgressOrder.getId());
        assertTrue(updatedOrder.isPresent());
        assertEquals(updatedOrder.get().getStatus(), Status.RESCHEDULE);
    }

    private String toJson(OrderStatusDto dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }

    private void clearTable() throws Exception{
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from ORDERS");
        preparedStatement.execute();
    }
}
