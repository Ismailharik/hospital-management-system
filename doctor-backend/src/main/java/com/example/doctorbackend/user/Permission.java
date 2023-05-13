package com.example.doctorbackend.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    DOCTOR_READ("management:read"),
    DOCTOR_UPDATE("management:update"),
    DOCTOR_CREATE("management:create"),
    DOCTOR_DELETE("management:delete")

    ;

    @Getter
    private final String permission;
}
