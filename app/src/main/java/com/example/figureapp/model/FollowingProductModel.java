package com.example.figureapp.model;

public class FollowingProductModel extends AbstractModel<FollowingProductModel>{
    private String idProduct;
    private User user;


    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    private ProductModel product;
}
