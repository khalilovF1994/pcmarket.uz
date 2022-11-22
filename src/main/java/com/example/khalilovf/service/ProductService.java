package com.example.khalilovf.service;

import com.example.khalilovf.entity.*;
import com.example.khalilovf.payload.ProductDto;
import com.example.khalilovf.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AttachmentRepository attachmentRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final DetailRepository detailRepository;


    public ProductDto create(ProductDto productDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty())
            throw new RuntimeException("Category id is null");
        Category category = optionalCategory.get();
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
        if (optionalAttachment.isEmpty())
            throw new RuntimeException("attachment id is null");
        Attachment attachment = optionalAttachment.get();
        Optional<Supplier> optionalSupplier = supplierRepository.findById(productDto.getSupplierId());
        if (optionalSupplier.isEmpty())
            throw new RuntimeException("supplier id is null");
        Supplier supplier = optionalSupplier.get();
        List<Detail> detailList = productDto.getDetailId()
                .stream()
                .map(detailRepository::getReferenceById)
                .toList();
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setAttachment(attachment);
        product.setSupplier(supplier);
        product.setDetails(detailList);
        product.setActive(productDto.isActive());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        Product save = productRepository.save(product);
        return ProductDto.toDto(save);
    }

    public ProductDto update(ProductDto productDto, Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            throw new RuntimeException("product id is null");
        Product product = optionalProduct.get();
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty())
            throw new RuntimeException("category id is null");
        Category category = optionalCategory.get();
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
        if (optionalAttachment.isEmpty())
            throw new RuntimeException("attachment id is null");
        Attachment attachment = optionalAttachment.get();
        Optional<Supplier> optionalSupplier = supplierRepository.findById(productDto.getSupplierId());
        if (optionalSupplier.isEmpty())
            throw new RuntimeException("supplier id is null");
        Supplier supplier = optionalSupplier.get();
        List<Detail> detailList = productDto.getDetailId()
                .stream()
                .map(detailRepository::getReferenceById)
                .toList();
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setAttachment(attachment);
        product.setSupplier(supplier);
        product.setDetails(detailList);
        product.setActive(productDto.isActive());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        return ProductDto.toDto(product);

    }

    public void delete(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            throw new RuntimeException("product id is null");
        Product product = optionalProduct.get();
        productRepository.delete(product);
    }

    public ProductDto getOne(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            throw new RuntimeException("product id is null");
        Product reference = productRepository.getReferenceById(id);
        return ProductDto.toDto(reference);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAllPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);


        return productRepository.findAll(pageable);

    }
}
