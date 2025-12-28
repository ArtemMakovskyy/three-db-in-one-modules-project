// file: application/src/main/java/com/store/application/service/AiResultService.java
package com.store.application.service;

import com.store.application.dto.AiResultDto;
import com.store.application.mapper.AiResultMapper;
import com.store.application.model.ai.AiResult;
import com.store.application.repository.ai.AiResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "aiTransactionManager")
public class AiResultService {

    private final AiResultRepository repository;

    public AiResultDto save(AiResultDto dto) {
        AiResult saved = repository.save(AiResultMapper.toEntity(dto));
        return AiResultMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<AiResultDto> getAll() {
        return repository.findAll()
                .stream()
                .map(AiResultMapper::toDto)
                .toList();
    }
}
