package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.repository;

import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    // Buscar por apellido paterno
    List<UserEntity> findByLastNameIgnoreCase(String lastName);

    // Buscar por DNI
    Optional<UserEntity> findByDni(String dni);

    // Buscar usuarios menores de una edad
    List<UserEntity> findByAgeLessThan(Integer age);
}
