# 🧩 Proyecto Hexagonal Amaya

Proyecto de **Spring Boot 3** con **Arquitectura Hexagonal**, seguridad por **roles**, y pruebas automatizadas con **JUnit + MockMvc**.

---

## 🚀 Características
- Arquitectura Hexagonal (domain / application / infrastructure)  
- Migraciones con **Flyway** y persistencia en **MySQL**  
- Seguridad con **Spring Security 6**  
- Roles: `ADMIN`, `MONITOR`, `USER`  
- Pruebas de controladores con autenticación Basic Auth  

---

## 🔐 Roles y permisos

| Rol | Permisos |
|------|-----------|
| **ADMIN** | Buscar por apellido paterno y DNI |
| **MONITOR** | Buscar menores de una edad |
| **USER** | Crear usuarios |

---

## 🧠 Endpoints

| Método | Endpoint | Descripción | Rol |
|---------|-----------|-------------|------|
| `POST` | `/api/users` | Crear usuario | USER / autenticado |
| `GET` | `/api/users/apellido/{lastName}` | Buscar por apellido | ADMIN |
| `GET` | `/api/users/dni/{dni}` | Buscar por DNI | ADMIN |
| `GET` | `/api/users/menores/{edad}` | Listar menores | MONITOR |

---

## 🔑 Credenciales de prueba
| Usuario | Contraseña | Rol |
|----------|-------------|------|
| admin | admin123 | ADMIN |
| monitor | monitor123 | MONITOR |
| user | user123 | USER |

---

## 🧪 Pruebas
En `UserControllerTest` se validan:
- Creación de usuario (USER)  
- Búsqueda por apellido y DNI (ADMIN)  
- Búsqueda por edad (MONITOR)

Todas las pruebas pasan ✅.

---

👨‍💻 Autor
Marcelo Leonardo Amaya Medina
Bootcamp Full Stack Java – CodiGo / Tecsup
Lima – Perú
