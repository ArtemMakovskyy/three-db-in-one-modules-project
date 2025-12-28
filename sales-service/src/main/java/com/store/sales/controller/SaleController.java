package com.store.sales.controller;

import com.store.sales.dto.SaleDto;
import com.store.sales.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public SaleDto create(@RequestBody SaleDto dto) {
        return saleService.create(dto);
    }

    @GetMapping
    public List<SaleDto> getAll() {
        return saleService.getAll();
    }
}
