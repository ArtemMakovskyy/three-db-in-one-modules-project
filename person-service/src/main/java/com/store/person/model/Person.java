package com.store.person.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;
}
