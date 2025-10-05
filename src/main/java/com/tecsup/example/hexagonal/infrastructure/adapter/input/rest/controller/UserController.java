package com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.controller;


import com.tecsup.example.hexagonal.application.port.input.UserService;
import com.tecsup.example.hexagonal.domain.exception.InvalidUserDataException;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundException;
import com.tecsup.example.hexagonal.domain.model.User;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserRequest;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserResponse;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // =======================================================
    // POST: Crear usuario
    // =======================================================
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody(required = false) UserRequest request) {
        try {
            if (request == null) {
                log.warn("Received null UserRequest");
                return ResponseEntity.badRequest().build();
            }

            log.info("Creating request with name: {} and email: {}", request.getName(), request.getEmail());
            User newUser = this.userMapper.toDomain(request);
            log.info("Mapped User entity: {}", newUser);

            User createdUser = this.userService.createUser(newUser);
            UserResponse response = this.userMapper.toResponse(createdUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (InvalidUserDataException e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // =======================================================
    // GET: Buscar usuario por ID
    // =======================================================
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        try {
            User user = this.userService.findUser(id);
            UserResponse response = this.userMapper.toResponse(user);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            log.warn("User not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error fetching user with ID: {}", id, e);
            return ResponseEntity.badRequest().build();
        }
    }

    // =======================================================
    // GET: Buscar usuarios por apellido paterno (ADMIN)
    // =======================================================
    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<UserResponse>> findByLastName(@PathVariable String apellido) {
        try {
            log.info("Searching users by last name: {}", apellido);

            List<User> users = userService.findByLastName(apellido);
            List<UserResponse> responses = users.stream()
                    .map(userMapper::toResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error searching users by last name: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // =======================================================
    // GET: Buscar usuario por DNI (ADMIN)
    // =======================================================
    @GetMapping("/dni/{dni}")
    public ResponseEntity<UserResponse> findByDni(@PathVariable String dni) {
        try {
            log.info("Searching user by DNI: {}", dni);

            Optional<User> optionalUser = userService.findByDni(dni);
            if (optionalUser.isPresent()) {
                UserResponse response = userMapper.toResponse(optionalUser.get());
                return ResponseEntity.ok(response);
            } else {
                log.warn("User not found with DNI: {}", dni);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error searching user by DNI: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // =======================================================
    // GET: Buscar usuarios menores de una edad (MONITOR)
    // =======================================================
    @GetMapping("/menores/{edad}")
    public ResponseEntity<List<UserResponse>> findMenoresDeEdad(@PathVariable Integer edad) {
        try {
            log.info("Searching users under age: {}", edad);

            List<User> users = userService.findMenoresDeEdad(edad);
            List<UserResponse> responses = users.stream()
                    .map(userMapper::toResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Error searching users under age {}: {}", edad, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}





























