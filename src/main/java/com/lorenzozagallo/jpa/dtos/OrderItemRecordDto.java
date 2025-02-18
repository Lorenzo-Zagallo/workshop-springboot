package com.lorenzozagallo.jpa.dtos;

public record OrderItemRecordDto(Long productID,
                                 Integer quantity,
                                 Double price) {
}
