package com.store.application.mapper;

import com.store.application.dto.PersonDto;
import com.store.application.model.person.Person;

public class PersonMapper {

    public static PersonDto toDto(Person entity) {
        if (entity == null) return null;
        return new PersonDto(
                entity.getId(),
                entity.getName(),
                entity.getAge()
        );
    }

    public static Person toEntity(PersonDto dto) {
        if (dto == null) return null;
        return new Person(
                dto.getId(),
                dto.getName(),
                dto.getAge()
        );
    }
}
