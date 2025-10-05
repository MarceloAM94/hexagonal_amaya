package com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.example.hexagonal.application.port.input.UserService;
import com.tecsup.example.hexagonal.domain.model.User;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserRequest;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserResponse;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.security.SecurityConfig;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    // =======================================================
    // TEST 1: CREAR USUARIO (requiere usuario autenticado)
    // =======================================================
    @Test
    void createUser() throws Exception {

        Long ID = 50L;
        String NAME = "Juana";
        String LASTNAME = "Perez";
        String MOTHERLASTNAME = "Gomez";
        Integer AGE = 25;
        String DNI = "98765432";
        String PHONE = "987654321";
        String EMAIL = "juana@demo.com";

        UserRequest request = new UserRequest(
                NAME, LASTNAME, MOTHERLASTNAME, AGE, DNI, PHONE, EMAIL
        );

        User newUser = new User(null, NAME, LASTNAME, MOTHERLASTNAME, AGE, DNI, PHONE, EMAIL);
        User savedUser = new User(ID, NAME, LASTNAME, MOTHERLASTNAME, AGE, DNI, PHONE, EMAIL);
        UserResponse response = new UserResponse(ID, NAME, LASTNAME, MOTHERLASTNAME, AGE, DNI, PHONE, EMAIL);

        when(userMapper.toDomain(request)).thenReturn(newUser);
        when(userService.createUser(newUser)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .with(httpBasic("user", "user123")) // 👈 autenticación USER
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.lastName").value(LASTNAME))
                .andExpect(jsonPath("$.motherLastName").value(MOTHERLASTNAME))
                .andExpect(jsonPath("$.dni").value(DNI))
                .andExpect(jsonPath("$.phoneNumber").value(PHONE))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andDo(print());
    }

    // =======================================================
    // TEST 2: BUSCAR POR APELLIDO PATERNO (solo ADMIN)
    // =======================================================
    @Test
    void getUsersByLastName() throws Exception {

        String LASTNAME = "Perez";

        User user1 = new User(1L, "Juana", LASTNAME, "Gomez", 25, "98765432", "999111222", "juana@demo.com");
        User user2 = new User(2L, "Carlos", LASTNAME, "Ramos", 30, "12345678", "988111333", "carlos@demo.com");

        when(userService.findByLastName(LASTNAME)).thenReturn(List.of(user1, user2));
        when(userMapper.toResponse(user1)).thenReturn(
                new UserResponse(1L, "Juana", LASTNAME, "Gomez", 25, "98765432", "999111222", "juana@demo.com"));
        when(userMapper.toResponse(user2)).thenReturn(
                new UserResponse(2L, "Carlos", LASTNAME, "Ramos", 30, "12345678", "988111333", "carlos@demo.com"));

        mockMvc.perform(get("/api/users/apellido/" + LASTNAME)
                        .with(httpBasic("admin", "admin123"))) // 👈 autenticación ADMIN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value(LASTNAME))
                .andExpect(jsonPath("$[1].lastName").value(LASTNAME))
                .andDo(print());
    }

    // =======================================================
    // TEST 3: BUSCAR POR DNI (solo ADMIN)
    // =======================================================
    @Test
    void getUserByDni() throws Exception {

        String DNI = "98765432";

        User user = new User(1L, "Juana", "Perez", "Gomez", 25, DNI, "999111222", "juana@demo.com");
        UserResponse response = new UserResponse(1L, "Juana", "Perez", "Gomez", 25, DNI, "999111222", "juana@demo.com");

        when(userService.findByDni(DNI)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        mockMvc.perform(get("/api/users/dni/" + DNI)
                        .with(httpBasic("admin", "admin123"))) // 👈 autenticación ADMIN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value(DNI))
                .andExpect(jsonPath("$.name").value("Juana"))
                .andDo(print());
    }

    // =======================================================
    // TEST 4: BUSCAR MENORES DE EDAD (solo MONITOR)
    // =======================================================
    @Test
    void getUsersMenoresDeEdad() throws Exception {

        int AGE_LIMIT = 18;

        User user1 = new User(1L, "Pedro", "Sanchez", "Lopez", 17, "12312312", "987111222", "pedro@demo.com");
        User user2 = new User(2L, "Lucia", "Sanchez", "Gomez", 15, "98798798", "987111333", "lucia@demo.com");

        when(userService.findMenoresDeEdad(AGE_LIMIT)).thenReturn(List.of(user1, user2));
        when(userMapper.toResponse(user1)).thenReturn(
                new UserResponse(1L, "Pedro", "Sanchez", "Lopez", 17, "12312312", "987111222", "pedro@demo.com"));
        when(userMapper.toResponse(user2)).thenReturn(
                new UserResponse(2L, "Lucia", "Sanchez", "Gomez", 15, "98798798", "987111333", "lucia@demo.com"));

        mockMvc.perform(get("/api/users/menores/" + AGE_LIMIT)
                        .with(httpBasic("monitor", "monitor123"))) // 👈 autenticación MONITOR
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(17))
                .andExpect(jsonPath("$[1].age").value(15))
                .andDo(print());
    }
}
