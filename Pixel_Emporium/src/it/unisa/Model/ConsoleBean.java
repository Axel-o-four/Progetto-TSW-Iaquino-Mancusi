package it.unisa.Model;

import java.io.Serializable;

public class ConsoleBean implements Serializable, Item {

    private static final long serialVersionUID = 1L;
    
    private String prefissoId;
    private int code;
    private String name;
    private String description;
    private String image;
    private String brand;
    private double price;
    private int releaseYear;
    private String support;
    private boolean retroCompatibility;
    private String storage;
    private int generation;
    private int quantity;
    
    public ConsoleBean() {
        this.prefissoId = "C";
        this.code = -1;
        this.name = "";
        this.description = "";
        this.image = "";
        this.brand = "";
        this.price = 0.0;
        this.releaseYear = 0;
        this.support = "";
        this.retroCompatibility = false;
        this.storage = "";
        this.generation = 0;
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }    

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }    

    public boolean isRetroCompatibility() {
        return retroCompatibility;
    }

    public void setRetroCompatibility(boolean retroCompatibility) {
        this.retroCompatibility = retroCompatibility;
    }    

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }    

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ConsoleBean [prefissoId=" + prefissoId 
                + ", code=" + code 
                + ", name=" + name 
                + ", description=" + description 
                + ", image=" + image 
                + ", brand=" + brand 
                + ", price=" + price 
                + ", releaseYear=" + releaseYear 
                + ", support=" + support 
                + ", retroCompatibility=" + retroCompatibility 
                + ", storage=" + storage 
                + ", generation=" + generation 
                + ", quantity=" + quantity + "]";
    }
}
