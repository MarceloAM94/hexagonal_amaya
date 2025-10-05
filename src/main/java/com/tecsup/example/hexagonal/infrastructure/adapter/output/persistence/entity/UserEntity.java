package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(length = 100)
    private String motherLastName; // Apellido materno

    @Column
    private Integer age;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 150)
    private String email;
}
