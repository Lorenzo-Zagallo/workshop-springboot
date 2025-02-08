package com.lorenzozagallo.jpa.dtos;

import java.util.UUID;

public record OrderItemRecordDto(UUID productID,
                                 Integer quantity,
                                 Double price) {
}
