package com.example.orders_service.domain.repository;

import com.example.orders_service.domain.Status;
import com.example.orders_service.domain.Order;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository {

    List<Order> findAll();

    Optional<Order> findById(Long id);

    List<Order> findByStatus(Status status);

    List<Order> searchById(Long id);

    Order save(Instant created, Status status);

    void updateOrderStatus(Long id, Status status);
}
