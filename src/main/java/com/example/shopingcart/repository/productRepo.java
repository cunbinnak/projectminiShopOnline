package com.example.shopingcart.repository;

import com.example.shopingcart.model.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface productRepo extends JpaRepository<product,Integer> {

    product findByProductId(Integer productId);

    Page<product> findAll(Pageable pageable);
}
