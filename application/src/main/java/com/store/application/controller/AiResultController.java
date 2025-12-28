// file: application/src/main/java/com/store/application/controller/AiResultController.java
package com.store.application.controller;

import com.store.application.dto.AiResultDto;
import com.store.application.service.AiResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiResultController {

    private final AiResultService aiResultService;

    @PostMapping
    public AiResultDto save(@RequestBody AiResultDto dto) {
        return aiResultService.save(dto);
    }

    @GetMapping
    public List<AiResultDto> getAll() {
        return aiResultService.getAll();
    }
}
