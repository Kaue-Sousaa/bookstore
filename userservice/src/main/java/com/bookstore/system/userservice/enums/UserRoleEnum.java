package com.bookstore.system.userservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    ADMIN(List.of("ROLE_ADMIN", "ROLE_LIBRARIAN", "ROLE_USER")),
    LIBRARIAN(List.of("ROLE_LIBRARIAN", "ROLE_USER")),
    USER(List.of("ROLE_USER"));

    private final List<String> authorities;
}
