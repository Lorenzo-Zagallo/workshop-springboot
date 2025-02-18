package com.lorenzozagallo.jpa.dtos;

import com.lorenzozagallo.jpa.models.enums.OrderStatus;

import java.time.Instant;
import java.util.List;

public record OrderRecordDto(Long id,
                             Instant moment,
                             OrderStatus orderStatus,
                             Long userID,
                             List<OrderItemRecordDto> items) {

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
