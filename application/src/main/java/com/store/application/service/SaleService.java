// file: application/src/main/java/com/store/application/service/SaleService.java
package com.store.application.service;

import com.store.application.dto.SaleDto;
import com.store.application.mapper.SaleMapper;
import com.store.application.model.sale.Sale;
import com.store.application.repository.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "salesTransactionManager")
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleDto create(SaleDto dto) {
        Sale saved = saleRepository.save(SaleMapper.toEntity(dto));
        return SaleMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SaleDto> getAll() {
        return saleRepository.findAll()
                .stream()
                .map(SaleMapper::toDto)
                .toList();
    }
}
