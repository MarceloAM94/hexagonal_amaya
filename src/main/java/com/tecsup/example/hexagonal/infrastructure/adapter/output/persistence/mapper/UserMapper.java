package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.mapper;


import com.tecsup.example.hexagonal.domain.model.User;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserRequest;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserResponse;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Convert User domain to UserEntity
     */
    UserEntity toEntity(User domain);

    /**
     * Convert UserEntity to User domain
     */
    User toDomain(UserEntity entity);

    /**
     * Convert UserRequest to User domain
     */
    @Mapping(target = "id", ignore = true) // El ID se genera automáticamente
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "motherLastName", source = "motherLastName")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "dni", source = "dni")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    User toDomain(UserRequest request);

    /**
     * Convert User domain to UserResponse
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "motherLastName", source = "motherLastName")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "dni", source = "dni")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    UserResponse toResponse(User user);
}
