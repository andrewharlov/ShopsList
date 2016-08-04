package com.harlov.shopslist;

public class ShopItem {
    private int id;
    private String brand;
    private String model;
    private String type;
    private double price;
    private int quantity;

    public ShopItem(int id, String brand, String model, String type,
                    double price, int quantity){
        this.setId(id);
        this.setBrand(brand);
        this.setModel(model);
        this.setType(type);
        this.setPrice(price);
        this.setQuantity(quantity);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
