package com.example.orders_service.api;

import com.example.orders_service.domain.Order;
import com.example.orders_service.domain.repository.OrdersRepository;
import com.example.orders_service.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersRepository ordersRepository;

    @GetMapping("/orders")
    public List<Order> getAll() {
        return ordersRepository.findAll();
    }

    @GetMapping("/orders/search")
    public List<Order> searchById(@RequestParam Long id) {
        return ordersRepository.searchById(id);
    }

    @GetMapping("/orders/filter")
    public List<Order> getByStatus(@RequestParam String status) {
        return ordersRepository.findByStatus(Status.fromString(status));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return ordersRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder() {
        Order order = ordersRepository.save(Instant.now(), Status.IN_PROGRESS);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/orders")
    public ResponseEntity<Void> updateOrderStatus(@RequestBody OrderStatusDto orderStatusDto) {
        if (!ordersRepository.findById(orderStatusDto.getId()).isPresent()) {
            return ResponseEntity.noContent()
                    .build();
        }
        Status status = Status.fromString(orderStatusDto.getStatus());
        ordersRepository.updateOrderStatus(orderStatusDto.getId(), status);
        return ResponseEntity.ok()
                .build();
    }

}
