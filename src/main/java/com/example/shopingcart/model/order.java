package com.example.shopingcart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name ="orders")
public class order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private int orderId;
    @Column(name = "username")
    private String userName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "orderdate")
    private Date orderDate;
    @Column(name = "shipdate")
    private Date shipDate;
    @Column(name = "orderstatus")
    private String orderStatus;

    @OneToMany(mappedBy = "orderId")
    private List<orderDetail> orderDetails;

    public order() {
    }

    public order(int orderId, String userName, String phone, String address, Date orderDate, Date shipDate, String orderStatus) {
        this.orderId = orderId;
        this.userName = userName;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<orderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<orderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
