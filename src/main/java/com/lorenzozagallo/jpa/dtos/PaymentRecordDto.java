package com.lorenzozagallo.jpa.dtos;

import java.util.Date;

public record PaymentRecordDto(Long orderId,
                              Date moment) {
}
