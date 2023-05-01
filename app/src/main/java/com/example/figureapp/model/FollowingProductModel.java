package com.example.figureapp.model;

public class FollowingProductModel {
    private int id;
    private String idProduct;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public FollowingProductModel(int id, String idProduct, User user, ProductModel product) {
        this.id = id;
        this.idProduct = idProduct;
        this.user = user;
        this.product = product;
    }

    private ProductModel product;
}
