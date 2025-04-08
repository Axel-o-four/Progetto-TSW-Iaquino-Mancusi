package it.unisa;

import java.io.Serializable;

public class ProductBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prefissoId; 
    private int code;          
    private String name;       
    private String description; 
    private double price;      
    private int quantity;      

    public ProductBean() {
        this.prefissoId = "";
        this.code = -1;
        this.name = "";
        this.description = "";
        this.price = 0.0;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductBean [prefissoId=" + prefissoId + ", code=" + code + ", name=" + name + ", description="
                + description + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
