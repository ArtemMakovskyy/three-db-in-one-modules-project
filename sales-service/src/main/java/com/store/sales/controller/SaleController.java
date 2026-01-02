package com.store.sales.controller;

import com.store.sales.dto.SaleDto;
import com.store.sales.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public SaleDto create(@RequestBody SaleDto dto) {
        return saleService.create(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public List<SaleDto> getAll() {
        return saleService.getAll();
    }
}