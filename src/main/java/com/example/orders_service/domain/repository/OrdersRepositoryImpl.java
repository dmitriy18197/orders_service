package com.example.orders_service.domain.repository;

import com.example.orders_service.domain.Status;
import com.example.orders_service.domain.Order;
import com.example.orders_service.domain.mapper.OrdersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepository {

    private final OrdersMapper ordersMapper;

    @Override
    public List<Order> findAll() {
        return ordersMapper.selectAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(ordersMapper.selectById(id));
    }

    @Override
    public List<Order> findByStatus(Status status) {
        return ordersMapper.selectByStatus(status);
    }

    @Override
    public List<Order> searchById(Long id) {
        String pattern = createSearchPattern(id);
        return ordersMapper.searchById(pattern);
    }

    private String createSearchPattern(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is required");
        }
        return "" + id + "%";
    }

    @Override
    public Order save(Instant created, Status status) {
        Order order = new Order(created, status);
        ordersMapper.insert(order);
        return order;
    }

    @Override
    public void updateOrderStatus(Long id, Status status) {
        ordersMapper.updateOrderStatus(id, status);
    }
}
