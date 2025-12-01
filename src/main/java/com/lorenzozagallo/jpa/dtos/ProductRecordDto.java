package com.lorenzozagallo.jpa.dtos;

public record ProductRecordDto(Long id,
        String name,
        String description,
        Double price,
        String imgUrl) {
}
