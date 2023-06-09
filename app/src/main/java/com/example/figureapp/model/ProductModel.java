package com.example.figureapp.model;


public class ProductModel extends AbstractModel<ProductModel> {
    private String name, description, imageProduct;
    private double price;
    private int quantity;

    public ProductModel(String name, int idCategory, String description, double price, int quantity) {
        super();
        this.name =name;
        this.idCategory = idCategory;
        this.description = description;
        this.price =price;
        this.quantity = quantity;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private int idCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
