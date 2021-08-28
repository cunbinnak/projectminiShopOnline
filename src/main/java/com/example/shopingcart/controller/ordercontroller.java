package com.example.shopingcart.controller;
import com.example.shopingcart.model.order;
import com.example.shopingcart.repository.orderRe;
import com.example.shopingcart.model.orderDetail;
import com.example.shopingcart.repository.orderDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/")
public class ordercontroller {

    @Autowired
    private orderRe orderRe;

    @Autowired
    private orderDetailRepo orderDetailRepo;

    @PostMapping(value = "/order")
    public String addOrder(@RequestBody order newOrder){
        order _Order = new order();
        _Order.setOrderDate(new Date());
        _Order.setAddress(newOrder.getAddress());
        _Order.setPhone(newOrder.getPhone());
        _Order.setShipDate(newOrder.getShipDate());
        _Order.setUserName(newOrder.getUserName());
        _Order.setOrderStatus("Đang chờ xét duyệt");

        orderRe.save(_Order);

        _Order.setOrderDetails(newOrder.getOrderDetails());
        for (orderDetail item: _Order.getOrderDetails()) {
            item.setOrderId(_Order.getOrderId());
            orderDetailRepo.save(item);

        }
        return "ok";
    }

    @GetMapping(value = "/order")
    public List<order> getListOrder(){
       return  orderRe.findAll();
    }


    @GetMapping(value = "/order/{orderid}")
    public order getOrderDetail(@PathVariable ("orderid") int orderid){

        Optional<order> data = orderRe.findById(orderid);
        if(data.isPresent()){
            order _order = data.get();
            List<orderDetail> listDetail =orderDetailRepo.findByOrderId(orderid);
            _order.setOrderDetails(listDetail);
            return  _order;
        }
        return null;
    }

    @PutMapping(value = "/admin/order")
    public String updateOrder(@RequestBody order editorder){
        order _Order = new order();
        _Order.setOrderId(editorder.getOrderId());
        _Order.setUserName(editorder.getUserName());
        _Order.setOrderDate(editorder.getOrderDate());
        _Order.setShipDate(editorder.getShipDate());
        _Order.setPhone(editorder.getPhone());
        _Order.setAddress(editorder.getAddress());
        _Order.setOrderStatus(editorder.getOrderStatus());

        orderRe.save(_Order);
        for (orderDetail detail: editorder.getOrderDetails()) {
            detail.setOrderId(editorder.getOrderId());
            orderDetailRepo.save(detail);
        }

        return "update success";
    }

    @DeleteMapping(value = "/admin/order/{orderid}")
    public void deleteOrder(@PathVariable int orderid){

        Optional<order> _Order = orderRe.findById(orderid);

        for (orderDetail item:_Order.get().getOrderDetails()) {
            item.setOrderId(_Order.get().getOrderId());
            orderDetailRepo.delete(item);
        }
        orderRe.delete(_Order.get());

    }
}
