package com.tecsup.example.hexagonal.application.service;

import com.tecsup.example.hexagonal.application.port.input.UserService;
import com.tecsup.example.hexagonal.application.port.output.UserRepository;
import com.tecsup.example.hexagonal.domain.exception.InvalidUserDataException;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundException;
import com.tecsup.example.hexagonal.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User newUser) {

        validateUserInput(newUser);
        return this.userRepository.save(newUser);
    }

    @Override
    public User findUser(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // 🆕 Buscar por apellido paterno
    @Override
    public List<User> findByLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new InvalidUserDataException("Last name cannot be empty");
        }
        return userRepository.findByLastName(lastName);
    }

    // 🆕 Buscar por DNI
    @Override
    public Optional<User> findByDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new InvalidUserDataException("DNI cannot be empty");
        }
        return userRepository.findByDni(dni);
    }

    // 🆕 Buscar los menores de cierta edad
    @Override
    public List<User> findMenoresDeEdad(Integer age) {
        if (age == null || age < 0) {
            throw new InvalidUserDataException("Age must be valid");
        }
        return userRepository.findByAgeLessThan(age);
    }

    private void validateUserInput(User newUser) {

        if (!newUser.hasValidName())
            throw new InvalidUserDataException("Invalid name");

        if (!newUser.hasValidEmail())
            throw new InvalidUserDataException("Invalid email");
    }
}
