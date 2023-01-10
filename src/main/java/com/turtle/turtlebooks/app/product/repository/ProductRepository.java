package com.turtle.turtlebooks.app.product.repository;

import com.turtle.turtlebooks.app.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
