//package org.example.service;
//
//import lombok.AllArgsConstructor;
//import org.example.dto.product.ProductItemDto;
////import org.example.dto.product.ProductItemDto;
//import org.example.dto.product.ProductPostDto;
////import org.example.dto.product.ProductPostDto;
//import org.example.dto.product.ProductPutDto;
//import org.example.entites.CategoryEntity;
//import org.example.entites.ProductEntity;
//import org.example.entites.ProductImageEntity;
//import org.example.mapper.IProductMapper;
////import org.example.mapper.ProductMapper;
//import org.example.repository.IProductImageRepository;
//import org.example.repository.IProductRepository;
//import org.springframework.stereotype.Service;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class ProductService {
//
//    private IProductRepository productRepository;
//    private FileService fileService;
//    private IProductImageRepository productImageRepository;
//    private IProductMapper productMapper;
//
//    public List<ProductItemDto> getAllProducts() {
//        var list = productRepository.findAll();
//        return productMapper.toDto(list);
//    }
//
//    public ProductItemDto getProductById(Integer id) {
//        return productMapper.toDto(productRepository.findById(id).orElse(null));
//    }
//
//    public ProductEntity createProduct(ProductPostDto product) {
//        var entity = new ProductEntity();
//        entity.setName(product.getName());
//        entity.setDescription(product.getDescription());
//        entity.setPrice(product.getPrice());
//        entity.setCreationTime(LocalDateTime.now());
//        var cat = new CategoryEntity();
//        cat.setId(product.getCategoryId());
//        entity.setCategory(cat);
//
//        productRepository.save(entity);
//
//        int priority = 1;
//        for (var img : product.getImages()) {
//            var imageName = fileService.load(img);
//            var img1 = new ProductImageEntity();
//            img1.setPriority(priority);
//            img1.setName(imageName);
//            img1.setProduct(entity);
//            productImageRepository.save(img1);
//            priority++;
//        }
//        return entity;
//    }
//
//    public boolean updateProduct(Integer id, ProductPutDto product) {
//        var res = productRepository.findById(id);
//        if (res.isEmpty()) {
//            return false;
//        }
//        var entity = res.get();
//        entity.setName(product.getName());
//        entity.setDescription(product.getDescription());
//        entity.setPrice(product.getPrice());
//        var cat = new CategoryEntity();
//        cat.setId(product.getCategoryId());
//        entity.setCategory(cat);
//        productRepository.save(entity);
//        for (var img : product.getRemoveImages()){
//            var removeImage = productImageRepository.findByName(img).get();
//            fileService.remove(img);
//            productImageRepository.delete(removeImage);
//        }
//
//        int priority = 1;
//        for (var img : product.getImages()) {
//            ProductImageEntity image = new ProductImageEntity();
//            var imageName = fileService.load(img);
//            image.setName(imageName);
//            image.setPriority(priority);
//            image.setProduct(entity);
//            productImageRepository.save(image);
//            priority++;
//        }
//
//
//        return true;
//    }
//
//    public boolean deleteProduct(Integer id) {
//        var res = productRepository.findById(id);
//        if (res.isEmpty()) {
//            return false;
//        }
//        var imgs = res.get().getImages();
//        for (var item : imgs)
//        {
//            fileService.remove(item.getName());
//            productImageRepository.delete(item);
//        }
//        productRepository.deleteById(id);
//        return true;
//    }
//}


package org.example.service;

import lombok.AllArgsConstructor;
//import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductItemDto;
//import org.example.dto.product.ProductPostDTO;

import org.example.dto.product.ProductPostDto;
import org.example.entites.CategoryEntity;
import org.example.entites.ProductEntity;
import org.example.entites.ProductImageEntity;
import org.example.mapper.IProductMapper;
//import org.example.mapper.ProductMapper;
import org.example.repository.IProductImageRepository;
import org.example.repository.IProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private IProductRepository productRepository;
    private FileService fileService;
    private IProductImageRepository productImageRepository;
    private IProductMapper productMapper;

    public List<ProductItemDto> getAllProducts() {
        var list = productRepository.findAll();
        return productMapper.toDto(list);
    }

    public ProductItemDto getProductById(Integer id) {
        return productMapper.toDto(productRepository.findById(id).orElse(null));
    }

    public ProductEntity createProduct(ProductPostDto product) {
        var entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setCreationTime(LocalDateTime.now());
        var cat = new CategoryEntity();
        cat.setId(product.getCategoryId());
        entity.setCategory(cat);

        productRepository.save(entity);

        int priority = 1;
        for (var img : product.getImages()) {
            var imageName = fileService.load(img);
            var img1 = new ProductImageEntity();
            img1.setPriority(priority);
            img1.setName(imageName);
            img1.setProduct(entity);
            productImageRepository.save(img1);
            priority++;
        }
        return entity;
    }

    public boolean updateProduct(Integer id, ProductPostDto product) {
        var entity = productRepository.findById(id).orElseThrow();

        // Оновлення основних даних продукту
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());

        var cat = new CategoryEntity();
        cat.setId(product.getCategoryId());
        entity.setCategory(cat);

        // Отримуємо список старих зображень у базі
        Map<String, ProductImageEntity> existingImages = entity.getImages().stream()
                .collect(Collectors.toMap(ProductImageEntity::getName, img -> img));

        List<ProductImageEntity> updatedImages = new ArrayList<>();

        for (int i = 0; i < product.getImages().size(); i++) {
            var img = product.getImages().get(i);

            if ("old-image".equals(img.getContentType())) {
                // Оновлення пріоритету старого зображення
                var imageName = img.getOriginalFilename();
                if (existingImages.containsKey(imageName)) {
                    var oldImage = existingImages.get(imageName);
                    oldImage.setPriority(i);
                    productImageRepository.save(oldImage);
                    updatedImages.add(oldImage);
                }
            } else {
                // Додавання нового зображення
                var imageName = fileService.load(img);
                var newImage = new ProductImageEntity();
                newImage.setName(imageName);
                newImage.setPriority(i);
                newImage.setProduct(entity);
                productImageRepository.save(newImage);
            }
        }
        List<Integer> removeIds = new ArrayList<>();
        // Видалення зображень, яких немає в оновленому списку
        for (var img : entity.getImages()) {
            if (!updatedImages.contains(img)) {
                fileService.remove(img.getName());
                removeIds.add(img.getId());
            }
        }

        productImageRepository.deleteAllByIdInBatch(removeIds);
        return true;
    }



//    public boolean updateProduct(Integer id, ProductPostDto product) {
//        var res = productRepository.findById(id);
//        if (res.isEmpty()) {
//            return false;
//        }
//        var entity = res.get();
//        entity.setName(product.getName());
//        entity.setDescription(product.getDescription());
//        entity.setPrice(product.getPrice());
//        var cat = new CategoryEntity();
//        cat.setId(product.getCategoryId());
//        entity.setCategory(cat);
//        productRepository.save(entity);
//
////        for (var img : product.getRemoveImages()) {
////            var removeImage = productImageRepository.findByName(img).get();
////            fileService.remove(img);
////            productImageRepository.delete(removeImage);
////        }
//
//        // Перевіряємо наявність і обробляємо видалення зображень
//        if (product.getRemoveImages() != null) {
//            for (var img : product.getRemoveImages()) {
//                var removeImage = productImageRepository.findByName(img);
//                if (removeImage.isPresent()) {
//                    // Видаляємо фото з сервера
//                    fileService.remove(img);
//                    // Видаляємо фото з бази даних
//                    productImageRepository.delete(removeImage.get());
//                }
//            }
//        }
//        List<ProductImageEntity> updatedImages = new ArrayList<>();
//        // Оновлюємо або додаємо нові зображення
//        int priority = 1;
//        for (var img : product.getImages()) {
//            ProductImageEntity image = new ProductImageEntity();
//            var imageName = fileService.load(img);
//            image.setName(imageName);
//            image.setPriority(priority);
//            image.setProduct(entity);
//            productImageRepository.save(image);
//            priority++;
//        }
//        List<Integer> removeIds = new ArrayList<>();
//        // Видалення зображень, яких немає в оновленому списку
//        for (var img : entity.getImages()) {
//            if (!updatedImages.contains(img)) {
//                fileService.remove(img.getName());
//                removeIds.add(img.getId());
//            }
//        }
//        productImageRepository.deleteAllByIdInBatch(removeIds);
//        return true;
//
////        int priority = 1;
////        for (var img : product.getImages()) {
////            ProductImageEntity image = new ProductImageEntity();
////            var imageName = fileService.load(img);
////            image.setName(imageName);
////            image.setPriority(priority);
////            image.setProduct(entity);
////            productImageRepository.save(image);
////            priority++;
////        }
////
////        return true;
//    }

    public boolean deleteProduct(Integer id) {
        var res = productRepository.findById(id);
        if (res.isEmpty()) {
            return false;
        }
        var imgs = res.get().getImages();
        for (var item : imgs)
        {
            fileService.remove(item.getName());
            productImageRepository.delete(item);
        }
        productRepository.deleteById(id);
        return true;
    }
}