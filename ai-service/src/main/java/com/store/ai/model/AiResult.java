package com.store.ai.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AiResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputData;

    private String outputData;
}
