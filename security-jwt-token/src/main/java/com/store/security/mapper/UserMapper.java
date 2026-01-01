package com.store.security.mapper;

import com.store.security.config.MapperConfig;
import com.store.security.dto.UserResponseDto;
import com.store.security.model.Role;
import com.store.security.model.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponseDto toDto(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }

    @AfterMapping
    default void setUserRoles(@MappingTarget UserResponseDto userDto, User user) {
        userDto.setRoles(mapRoles(user.getRoles()));
    }
}
