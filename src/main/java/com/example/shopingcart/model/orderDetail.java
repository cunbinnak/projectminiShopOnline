package com.example.shopingcart.model;




import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orderdetail")
public class orderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderdetailid")
    private int orderDetailId;

    private int orderId;
    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "productid")
    private product product;
    @Column(name = "quantity")
    private int quantity;


    public orderDetail(int orderDetailId, int orderId, com.example.shopingcart.model.product product, int quantity) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;

    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public com.example.shopingcart.model.product getProduct() {
        return product;
    }

    public void setProduct(com.example.shopingcart.model.product product) {
        this.product = product;
    }

    public orderDetail() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
