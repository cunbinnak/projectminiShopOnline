package com.example.shopingcart.model;




import javax.persistence.*;


@Entity
@Table(name = "products")
public class product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private int productId;
    @Column(name = "productname")
    private String productName;
    @Column(name = "price")
    private float price;
    @Column(name = "urlimg")
    private String urlImg;



//    @OneToMany(mappedBy = "productId")
//    private List<orderDetail> orderDetails;

    public product() {
    }

    public product(int productId, String productName, float price, String urlImg) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.urlImg = urlImg;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
