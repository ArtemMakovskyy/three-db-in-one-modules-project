package com.store.person.controller;

import com.store.person.dto.PersonDto;
import com.store.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/testit")
    public String test(){
        return "test it";
    }

    @PostMapping
    public PersonDto create(@RequestBody PersonDto dto) {
        return personService.create(dto);
    }

    @GetMapping("/{id}")
    public PersonDto getById(@PathVariable Long id) {
        return personService.getById(id);
    }

    @GetMapping
    public List<PersonDto> getAll() {
        return personService.getAll();
    }

    @PutMapping("/{id}")
    public PersonDto update(
            @PathVariable Long id,
            @RequestBody PersonDto dto
    ) {
        return personService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }
}
