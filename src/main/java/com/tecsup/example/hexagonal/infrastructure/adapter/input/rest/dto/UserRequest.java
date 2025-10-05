package com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String lastName;        // Apellido paterno
    private String motherLastName;  // Apellido materno
    private Integer age;
    private String dni;
    private String phoneNumber;
    private String email;
}
