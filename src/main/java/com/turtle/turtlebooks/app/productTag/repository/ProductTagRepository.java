package com.turtle.turtlebooks.app.productTag.repository;

import com.turtle.turtlebooks.app.productTag.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag , Long> {
}
