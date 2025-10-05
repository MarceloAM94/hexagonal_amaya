package com.tecsup.example.hexagonal.application.port.input;

import com.tecsup.example.hexagonal.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User newUser);

    User findUser(Long id);

    // Buscar por apellido paterno
    List<User> findByLastName(String lastName);

    // Buscar por DNI
    Optional<User> findByDni(String dni);

    // Buscar usuarios menores de una edad
    List<User> findMenoresDeEdad(Integer age);
}
