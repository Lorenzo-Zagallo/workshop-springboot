package com.lorenzozagallo.jpa.dtos;

import com.lorenzozagallo.jpa.models.enums.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderRecordDto(UUID id,
                             Instant moment,
                             Integer orderStatus,
                             UUID userID,
                             List<OrderItemRecordDto> items) {

    public OrderStatus getOrderStatus() {
        return OrderStatus.valueOf(orderStatus); // convertendo manualmente
    }
}
