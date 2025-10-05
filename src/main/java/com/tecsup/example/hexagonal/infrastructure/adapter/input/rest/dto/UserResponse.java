package com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String lastName;        // Apellido paterno
    private String motherLastName;  // Apellido materno
    private Integer age;
    private String dni;
    private String phoneNumber;
    private String email;
}
