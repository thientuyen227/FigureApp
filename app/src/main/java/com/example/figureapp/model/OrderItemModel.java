package com.example.figureapp.model;

public class OrderItemModel {
    private int ordersId ;
    private int productId;
    private int count;
    private OrderModel orders;

    public OrderItemModel(int ordersId, int productId, int count, OrderModel orders, ProductModel product) {
        this.ordersId = ordersId;
        this.productId = productId;
        this.count = count;
        this.orders = orders;
        this.product = product;
    }

    private ProductModel product;

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OrderModel getOrders() {
        return orders;
    }

    public void setOrders(OrderModel orders) {
        this.orders = orders;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
}
