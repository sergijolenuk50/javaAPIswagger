package org.example.dto.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductPostDto {
    private String name;
    private String description;
    private double price;
    private int categoryId;
    //модуль щоб створювати фото в продукті
    private List<MultipartFile> images;
}
