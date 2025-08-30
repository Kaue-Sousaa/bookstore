package com.bookstore.system.userservice.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(

        @NotBlank(message = "name cannot be blank")
        String name,

        @NotBlank(message = "email cannot be blank")
        String email,

        @NotBlank(message = "phone cannot be blank")
        String phone,

        @NotBlank(message = "password cannot be blank")
        String password
) {
}
