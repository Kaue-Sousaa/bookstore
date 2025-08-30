package com.bookstore.system.userservice.dtos;

public record UserDto(
        Long id,
        String name,
        String email,
        String phone) {
}
