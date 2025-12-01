package com.lorenzozagallo.jpa.dtos;

public record UserRecordDto(Long id,
        String name,
        String email,
        String phone,
        String password) {
}
