package com.lorenzozagallo.jpa.dtos;

import com.lorenzozagallo.jpa.models.enums.OrderStatus;

import java.util.Date;
import java.util.List;

public record OrderRecordDto(Long id,
        Date moment,
        OrderStatus orderStatus,
        Long clientId,
        List<OrderItemRecordDto> items) {

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
