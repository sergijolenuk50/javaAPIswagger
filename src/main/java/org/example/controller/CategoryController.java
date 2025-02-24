//package org.example.controller;
//
//import org.example.entites.CategoryEntity;
//import org.example.repository.ICategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/categories")
//public class CategoryController {
//
//    @Autowired
//    private ICategoryRepository repository;
//
//    @GetMapping
//    public List<CategoryEntity> getAllCategories() {
//        return repository.findAll();
//    }
//
//}


//package org.example.controller;
//
//import org.example.entites.CategoryEntity;
//import org.example.repository.ICategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;  // Імпорт для LocalDateTime
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/categories")
//public class CategoryController {
//
//    @Autowired
//    private ICategoryRepository repository;
//
//    // Отримати всі категорії
//    @GetMapping
//    public List<CategoryEntity> getAllCategories() {
//        return repository.findAll();
//    }
//
//    // Отримати категорію за ID
//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Integer id) {
//        Optional<CategoryEntity> category = repository.findById(id);
//        return category.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Створити нову категорію
//    @PostMapping
//    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
//        category.setCreationTime(LocalDateTime.now());  // Використовуємо LocalDateTime
//        CategoryEntity savedCategory = repository.save(category);
//        return ResponseEntity.status(201).body(savedCategory);
//    }
//
//    // Оновити існуючу категорію
//    @PutMapping("/{id}")
//    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity updatedCategory) {
//        return repository.findById(id)
//                .map(existingCategory -> {
//                    existingCategory.setName(updatedCategory.getName());
//                    existingCategory.setImage(updatedCategory.getImage());
//                    existingCategory.setDescription(updatedCategory.getDescription());
//                    existingCategory.setCreationTime(LocalDateTime.now()); // Оновити час створення, якщо потрібно
//                    CategoryEntity savedCategory = repository.save(existingCategory);
//                    return ResponseEntity.ok(savedCategory);
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Видалити категорію
//    @DeleteMapping("/{id}")
//    public void deleteCategory(@PathVariable Integer id) {
//        var entity = repository.findById(id).get();
//        repository.delete(entity);
//    }
//}

package org.example.controller;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.entites.CategoryEntity;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryItemDTO> getAllCategories() {
        return categoryService.getList();
    }

    @PostMapping (consumes = MULTIPART_FORM_DATA_VALUE) //в запиті будуть передаватись файли в тілі запиту
    public CategoryEntity create(@ModelAttribute CategoryCreateDTO dto) {
        return categoryService.create(dto);
    }
    //ПЕРЕРОБЛЮЄМО КОНТРОЛЛЕР
//    @PutMapping
//    public CategoryEntity edit(@RequestBody CategoryEditDTO dto) {
//        return categoryService.edit(dto);
//    }

    @PutMapping(path="/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public CategoryEntity edit(@PathVariable int id, @ModelAttribute CategoryEditDTO dto) {
        dto.setId(id);
        return categoryService.edit(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        categoryService.delete(id);
    }

    @GetMapping("/{id}")
    public CategoryItemDTO getById(@PathVariable int id) {
        return categoryService.getById(id);
    }
}
