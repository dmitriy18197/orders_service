package com.example.orders_service.domain;

import java.util.Arrays;
import java.util.Objects;

public enum Status {
    DELIVERED, IN_PROGRESS, RESCHEDULE;

    public static Status fromString(String value) {
        return Arrays.stream(values())
                .filter(status -> Objects.equals(status.name(), value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported status : " + value));
    }
}
