package com.store.person.mapper;

import com.store.person.dto.PersonDto;
import com.store.person.model.Person;

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
