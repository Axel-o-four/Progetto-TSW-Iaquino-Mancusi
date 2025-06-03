package it.unisa;

import java.io.Serializable;

public class GiocoBean implements Serializable, Item {

    private static final long serialVersionUID = 1L;
    
    private String prefissoId;
    private int code;  
    private String name;
    private String description;
    private String image;
    private String brand;
    private double price;
    private int releaseYear;
    private String genre;
    private String pegi;
    private String format;
    private int quantity;
    
    public GiocoBean() {
        this.prefissoId = "G";
        this.code = -1;  
        this.name = "";
        this.description = "";
        this.image = "";
        this.brand = "";
        this.price = 0.0;
        this.releaseYear = 0;
        this.genre = "";
        this.pegi = "";
        this.format = "FISICO";
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }    

    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }    

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "GiocoBean [prefissoId=" + prefissoId + ", code=" + code + ", name=" + name 
                + ", description=" + description + ", image=" + image + ", brand=" + brand 
                + ", price=" + price + ", releaseYear=" + releaseYear + ", genre=" + genre 
                + ", pegi=" + pegi + ", format=" + format + ", quantity=" + quantity + "]";
    }
}
