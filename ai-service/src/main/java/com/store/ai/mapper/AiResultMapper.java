package com.store.ai.mapper;

import com.store.ai.dto.AiResultDto;
import com.store.ai.model.AiResult;

public class AiResultMapper {

    public static AiResult toEntity(AiResultDto dto) {
        if (dto == null) return null;
        return AiResult.builder()
                .id(dto.getId())
                .inputData(dto.getInputData())
                .outputData(dto.getOutputData())
                .build();
    }

    public static AiResultDto toDto(AiResult entity) {
        if (entity == null) return null;
        return AiResultDto.builder()
                .id(entity.getId())
                .inputData(entity.getInputData())
                .outputData(entity.getOutputData())
                .build();
    }
}
