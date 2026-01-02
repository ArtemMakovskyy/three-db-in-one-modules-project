package com.store.ai.controller;

import com.store.ai.dto.AiResultDto;
import com.store.ai.service.AiResultService;
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
