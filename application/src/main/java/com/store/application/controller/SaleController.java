// file: application/src/main/java/com/store/application/controller/SaleController.java
package com.store.application.controller;

import com.store.application.dto.SaleDto;
import com.store.application.service.SaleService;
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
