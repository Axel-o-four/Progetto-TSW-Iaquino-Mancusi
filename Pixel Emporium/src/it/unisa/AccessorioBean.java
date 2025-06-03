package it.unisa;

import java.io.Serializable;

public class AccessorioBean implements Serializable, Item {

    private static final long serialVersionUID = 1L;
    
    private String prefissoId;
    private int code;
    private String name;
    private String description;
    private String image;
    private String brand;
    private double price;
    private String accessoryType;
    private int quantity;
    
    public AccessorioBean() {
        this.prefissoId = "A";
        this.code = -1;
        this.name = "";
        this.description = "";
        this.image = "";
        this.brand = "";
        this.price = 0.0;
        this.accessoryType = "";
        this.quantity = 0;
    }
    
    public String getPrefissoId() {
        return prefissoId;
    }
    
    public void setPrefissoId(String prefissoId) {
        this.prefissoId = prefissoId;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
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
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getAccessoryType() {
        return accessoryType;
    }
    
    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "AccessorioBean [prefissoId=" + prefissoId 
                + ", code=" + code 
                + ", name=" + name 
                + ", description=" + description 
                + ", image=" + image 
                + ", brand=" + brand 
                + ", price=" + price 
                + ", accessoryType=" + accessoryType 
                + ", quantity=" + quantity + "]";
    }
}
