package org.example.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductItemDto {
    private Integer id;
    private String name;
    private String description;
    private float price;
    private String categoryName;
    private String dateCreated;
    private Integer CategoryId;
    private List<ProductImageDto> images;
}
