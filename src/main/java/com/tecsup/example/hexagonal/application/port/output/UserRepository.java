package com.tecsup.example.hexagonal.application.port.output;

import com.tecsup.example.hexagonal.domain.model.User;

import java.util.Optional;
import java.util.List;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    // 🆕 Buscar por apellido paterno
    List<User> findByLastName(String lastName);

    // 🆕 Buscar por DNI
    Optional<User> findByDni(String dni);

    // 🆕 Buscar los menores de cierta edad
    List<User> findByAgeLessThan(Integer age);
}
