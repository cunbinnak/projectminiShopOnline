package com.example.shopingcart.repository;

import com.example.shopingcart.model.orderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface orderDetailRepo extends JpaRepository<orderDetail,Integer> {

    List<orderDetail> findByOrderId(int orderId);


}
