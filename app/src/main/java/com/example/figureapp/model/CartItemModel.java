package com.example.figureapp.model;

public class CartItemModel {
    private int id;
    private int cartId;
    private int productId;
    private int cout;
    private ProductModel productModel;

    public CartItemModel(int id, int cartId, int productId, int cout, ProductModel productModel) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.cout = cout;
        this.productModel = productModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }
}
