package org.example.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductPutDto extends ProductPostDto {
    //Список фото, які ми маємо видялатися при Edit
    private List<String> removeImages;
}