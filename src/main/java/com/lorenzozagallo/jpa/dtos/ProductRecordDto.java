package com.lorenzozagallo.jpa.dtos;

import java.util.UUID;

public record ProductRecordDto(UUID id,
                               String name,
                               String description,
                               Double price,
                               String imgUrl) {
}
