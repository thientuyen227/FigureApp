package com.example.figureapp.model;

import java.util.List;

public class CartModel {
    private int id;
    private int userId;
    private int storeId;
    private List<CartItemModel> cartItemModels;

    public CartModel(int id, int userId, int storeId, List<CartItemModel> cartItemModels) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.cartItemModels = cartItemModels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public List<CartItemModel> getCartItemModels() {
        return cartItemModels;
    }

    public void setCartItemModels(List<CartItemModel> cartItemModels) {
        this.cartItemModels = cartItemModels;
    }
}
