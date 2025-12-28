// file: application/src/main/java/com/store/application/service/AiResultService.java
package com.store.ai.service;

import com.store.ai.dto.AiResultDto;
import com.store.ai.mapper.AiResultMapper;
import com.store.ai.model.AiResult;
import com.store.ai.repository.AiResultRepository;
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
