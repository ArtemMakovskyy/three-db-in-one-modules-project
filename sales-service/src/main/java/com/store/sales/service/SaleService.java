// file: application/src/main/java/com/store/application/service/SaleService.java
package com.store.sales.service;

import com.store.sales.dto.SaleDto;
import com.store.sales.mapper.SaleMapper;
import com.store.sales.model.sale.Sale;
import com.store.sales.repository.sale.SaleRepository;
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
