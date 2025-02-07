package com.lorenzozagallo.jpa.dtos;

import java.util.UUID;

public record UserRecordDto(UUID id,
                            String name,
                            String email,
                            String phone,
                            String password) {
}
