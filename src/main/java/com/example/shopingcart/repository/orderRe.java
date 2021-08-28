package com.example.shopingcart.repository;

import com.example.shopingcart.model.order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderRe extends JpaRepository<order,Integer> {
}
