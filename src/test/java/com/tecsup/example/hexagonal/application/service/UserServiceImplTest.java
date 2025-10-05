package com.tecsup.example.hexagonal.application.service;

import com.tecsup.example.hexagonal.application.port.output.UserRepository;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundException;
import com.tecsup.example.hexagonal.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl(userRepository);
    }

    // =======================================================
    // TEST 1: Crear Usuario
    // =======================================================
    @Test
    void createUser() {

        Long ID = 50L;
        String NAME = "Juana";
        String LASTNAME = "Perez";
        String MOTHERLASTNAME = "Gomez";
        Integer AGE = 25;
        String DNI = "98765432";
        String PHONE = "999111222";
        String EMAIL = "juana@demo.com";

        // Usuario nuevo (sin ID)
        User newUser = new User(null, NAME, LASTNAME, MOTHERLASTNAME, AGE, DNI, PHONE, EMAIL);

        // Usuario guardado (con ID)
        User savedUser = new User(ID, NAME, LASTNAME, MOTHERLASTNAME, AGE, DNI, PHONE, EMAIL);

        // Mock del repositorio
        when(userRepository.save(newUser)).thenReturn(savedUser);

        // Ejecutar método del servicio
        User realUser = userService.createUser(newUser);

        // Validar resultados
        assertNotNull(realUser);
        assertEquals(ID, realUser.getId());
        assertEquals(NAME, realUser.getName());
        assertEquals(LASTNAME, realUser.getLastName());
        assertEquals(MOTHERLASTNAME, realUser.getMotherLastName());
        assertEquals(DNI, realUser.getDni());
        assertEquals(PHONE, realUser.getPhoneNumber());
        assertEquals(EMAIL, realUser.getEmail());
    }

    // =======================================================
    // TEST 2: Buscar Usuario por ID
    // =======================================================
    @Test
    void findUser() {
        Long ID = 100L;
        String NAME = "Jaime";
        String LASTNAME = "Perez";
        String MOTHERLASTNAME = "Lopez";
        Integer AGE = 30;
        String DNI = "12345678";
        String PHONE = "987654321";
        String EMAIL = "jaime@demo.com";

        User existingUser = new User(ID, NAME, LASTNAME, MOTHERLASTNAME, AGE, DNI, PHONE, EMAIL);

        when(userRepository.findById(ID)).thenReturn(Optional.of(existingUser));

        User realUser = userService.findUser(ID);

        assertNotNull(realUser);
        assertEquals(ID, realUser.getId());
        assertEquals(NAME, realUser.getName());
        assertEquals(LASTNAME, realUser.getLastName());
        assertEquals(EMAIL, realUser.getEmail());
    }

    // =======================================================
    // TEST 3: Buscar Usuario por ID - No existe
    // =======================================================
    @Test
    void findUser_NotFound() {
        Long UNKNOWN_ID = 999L;

        when(userRepository.findById(UNKNOWN_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findUser(UNKNOWN_ID));
    }
}













