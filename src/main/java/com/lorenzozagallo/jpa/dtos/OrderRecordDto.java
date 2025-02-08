package com.lorenzozagallo.jpa.dtos;

import com.lorenzozagallo.jpa.models.User;
import com.lorenzozagallo.jpa.models.enums.OrderStatus;

import java.time.Instant;
import java.util.UUID;

public record OrderRecordDto(UUID id,
                             Instant moment,
                             OrderStatus orderStatus,
                             User user) {
}
