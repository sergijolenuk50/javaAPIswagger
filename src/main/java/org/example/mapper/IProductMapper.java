//package org.example.mapper;
//
//import org.example.dto.product.ProductItemDto;
//import org.example.dto.product.ProductItemDto;
//import org.example.entites.ProductEntity;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface IProductMapper {
//
//    @Mapping(source = "creationTime", target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
//    @Mapping(source = "category.name", target = "categoryName")
//    @Mapping(source = "category.id", target = "categoryId")
//
//    ProductItemDto toDto(ProductEntity product);
//
//    List<ProductItemDto> toDto(List<ProductEntity> products);
//
//
//}

package org.example.mapper;

import org.example.dto.product.ProductImageDto;
import org.example.dto.product.ProductItemDto;
import org.example.entites.ProductEntity;
import org.example.entites.ProductImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    default List<ProductImageDto> toStr(List<ProductImageEntity> productImageEntities) {
        return productImageEntities == null
                ? Collections.emptyList()
                : productImageEntities.stream()
                .sorted(Comparator.comparing(ProductImageEntity::getPriority))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProductImageDto toDto(ProductImageEntity entity) {
        return new ProductImageDto(entity.getId(), entity.getName(), entity.getPriority(), entity.getProduct().getId()); // Adjust as needed
    }

    @Mapping(source = "creationTime", target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "category.id", target = "categoryId")
    ProductItemDto toDto(ProductEntity product);

    List<ProductItemDto> toDto(List<ProductEntity> products);

}