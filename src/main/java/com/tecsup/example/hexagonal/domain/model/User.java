package com.tecsup.example.hexagonal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String lastName;
    private String motherLastName; // 🆕 Apellido materno
    private Integer age;           // 🆕 Edad
    private String dni;            // 🆕 DNI
    private String phoneNumber;    // 🆕 Teléfono
    private String email;

    // ======= Business logic (dominio puro) =======

    public boolean hasValidEmail() {
        return email != null &&
                email.contains("@") &&
                email.contains(".") &&
                email.length() > 5;
    }

    public boolean hasValidName() {
        return name != null &&
                !name.trim().isEmpty() &&
                name.length() >= 2;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", motherLastName='" + motherLastName + '\'' +
                ", age=" + age +
                ", dni='" + dni + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
