package com.duong.productmanager.model;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm {
    private Long id;
    private String name;
    private int number;
    private double price;
    private MultipartFile image;
    private Category category;

    public ProductForm() {
    }

    public ProductForm(Long id, String name, int number, double price, MultipartFile image, Category category) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public ProductForm(String name, int number, double price, MultipartFile image, Category category) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
