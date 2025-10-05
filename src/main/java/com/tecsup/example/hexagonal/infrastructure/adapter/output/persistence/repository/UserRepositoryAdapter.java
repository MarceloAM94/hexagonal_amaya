package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.repository;

import com.tecsup.example.hexagonal.application.port.output.UserRepository;
import com.tecsup.example.hexagonal.domain.model.User;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = this.userMapper.toEntity(user);
        UserEntity entityCreated = this.jpaRepository.save(userEntity);
        log.info("User created: {}", entityCreated);
        return this.userMapper.toDomain(entityCreated);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.jpaRepository.findById(id)
                .map(this.userMapper::toDomain);
    }

    // 🆕 Buscar por apellido paterno
    @Override
    public List<User> findByLastName(String lastName) {
        return this.jpaRepository.findByLastNameIgnoreCase(lastName)
                .stream()
                .map(this.userMapper::toDomain)
                .collect(Collectors.toList());
    }

    // 🆕 Buscar por DNI
    @Override
    public Optional<User> findByDni(String dni) {
        return this.jpaRepository.findByDni(dni)
                .map(this.userMapper::toDomain);
    }

    // 🆕 Buscar usuarios menores de una edad
    @Override
    public List<User> findByAgeLessThan(Integer age) {
        return this.jpaRepository.findByAgeLessThan(age)
                .stream()
                .map(this.userMapper::toDomain)
                .collect(Collectors.toList());
    }
}
