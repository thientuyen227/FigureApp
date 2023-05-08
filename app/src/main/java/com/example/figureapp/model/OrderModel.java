package com.example.figureapp.model;

import java.util.List;

public class OrderModel extends AbstractModel<OrderModel> {
    private int userId;
    private int storeId;
    private User user;
    private List<OrderItemModel> orderitems;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItemModel> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<OrderItemModel> orderitems) {
        this.orderitems = orderitems;
    }
}
