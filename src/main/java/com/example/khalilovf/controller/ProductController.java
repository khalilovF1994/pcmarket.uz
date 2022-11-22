package com.example.khalilovf.controller;

import com.example.khalilovf.entity.Product;
import com.example.khalilovf.payload.ProductDto;
import com.example.khalilovf.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping("/create")
    public ProductDto create(@RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/update")
    public ProductDto update(@RequestBody ProductDto productDto, @PathVariable Integer id) {
        return productService.update(productDto, id);
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        productService.delete(id);
        return "product success deleted";
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping("/{id}")
    public ProductDto getOne(@PathVariable Integer id) {
        return productService.getOne(id);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping("/get/page")
    public Page<Product> getPage(@RequestParam Integer size, @RequestParam Integer page) {
        return productService.findAllPage(size, page);
    }
}