package com.example.figureapp.model;

public class CartItemModel extends AbstractModel<CartItemModel>{
    private int cartId    ;
    private int productId ;
    private int count     ;
    private ProductModel product;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ProductModel getProductModel() {
        return product;
    }

    public void setProductModel(ProductModel product) {
        this.product = product;
    }

}
