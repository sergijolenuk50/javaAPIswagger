package org.example.repository;

import org.example.entites.CategoryEntity;
import org.example.entites.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity,Integer> {
}
