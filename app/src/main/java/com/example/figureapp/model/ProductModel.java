package com.example.figureapp.model;

public class ProductModel {
    private String name, description, imageProduct;
    private int id;
    private int idProduct;
    private int idCategory;

    public ProductModel(String name, String description, String imageProduct, int id, int idProduct, int idCategory) {
        this.name = name;
        this.description = description;
        this.imageProduct = imageProduct;
        this.id = id;
        this.idProduct = idProduct;
        this.idCategory = idCategory;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
