package com.store.application.service;

import com.store.application.dto.PersonDto;
import com.store.application.mapper.PersonMapper;
import com.store.application.model.person.Person;
import com.store.application.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "personTransactionManager")
public class PersonService {

    private final PersonRepository personRepository;

    public PersonDto create(PersonDto dto) {
        Person saved = personRepository.save(PersonMapper.toEntity(dto));
        return PersonMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public PersonDto getById(Long id) {
        return personRepository.findById(id)
                .map(PersonMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    @Transactional(readOnly = true)
    public List<PersonDto> getAll() {
        return personRepository.findAll()
                .stream()
                .map(PersonMapper::toDto)
                .toList();
    }

    public PersonDto update(Long id, PersonDto dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(dto.getName());
        person.setAge(dto.getAge());
        return PersonMapper.toDto(personRepository.save(person));
    }

    public void delete(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("Person not found");
        }
        personRepository.deleteById(id);
    }
}
