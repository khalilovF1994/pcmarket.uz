package com.example.khalilovf.payload;

import com.example.khalilovf.entity.Detail;
import com.example.khalilovf.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String name;

    private Integer categoryId;

    private Integer attachmentId;

    private Integer supplierId;

    private List<Integer> detailId;

    private boolean active;

    private Double price;

    private String description;

    public static ProductDto toDto(Product product){
        ProductDto productDto=new ProductDto();
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setAttachmentId(product.getAttachment().getId());
        productDto.setSupplierId(product.getSupplier().getId());
        List<Integer> detailsList=new ArrayList<>();
        for (Detail details : product.getDetails()) {
            detailsList.add(details.getId());
        }
        productDto.setDetailId(detailsList);
        productDto.setActive(product.isActive());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        return productDto;
    }
}

