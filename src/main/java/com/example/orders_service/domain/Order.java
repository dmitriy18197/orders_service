package com.example.orders_service.domain;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Instant created;
    private Status status;

    public Order(Instant created, Status status) {
        this.created = created;
        this.status = status;
    }
}
