package org.example.mapper;

import org.example.dto.product.ProductItemDto;
import org.example.dto.product.ProductItemDto;
import org.example.entites.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    @Mapping(source = "creationTime", target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "category.name", target = "categoryName")
    ProductItemDto toDto(ProductEntity product);

    List<ProductItemDto> toDto(List<ProductEntity> products);


}
