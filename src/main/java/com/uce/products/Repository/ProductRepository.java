package com.uce.products.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uce.products.Model.ProductModel;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    ProductModel findByName(String name);
}
