package it.unisa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    private List<Item> items;
    
    public Cart() {
        items = new ArrayList<>();
    }
    
    public void addProduct(Item product) {
        boolean found = false;
        for (Item item : items) {
            if (item.getCode() == product.getCode()) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            if (product.getQuantity() <= 0) {
                product.setQuantity(1);
            }
            items.add(product);
        }
    }
    
    public void addProduct(Item product, int quantity) {
        boolean found = false;
        for (Item item : items) {
            if (item.getCode() == product.getCode() &&
                item.getPrefissoId().equals(product.getPrefissoId())) {
                
                int newTotal = item.getQuantity() + quantity;
                if (newTotal > product.getQuantity()) {
                    System.out.println("Quantità richiesta superiore a quella disponibile. Aggiungo solo il massimo possibile.");
                    item.setQuantity(product.getQuantity());
                } else {
                    item.setQuantity(newTotal);
                }
                found = true;
                break;
            }
        }
        if (!found) {
            if (quantity > product.getQuantity()) {
                System.out.println("Quantità richiesta superiore a quella disponibile. Aggiungo solo il massimo disponibile.");
                product.setQuantity(product.getQuantity());
            } else {
                product.setQuantity(quantity);
            }
            items.add(product);
        }
    }



    
    public void deleteProduct(Item product) {
        Iterator<Item> it = items.iterator();
        while(it.hasNext()){
            Item current = it.next();
            if(current.getCode() == product.getCode()){
                it.remove();
                break;
            }
        }
    }
    
    public void clear() {
        items.clear();
    }
    
    public int getTotalQuantity() {
        int total = 0;
        for (Item item : items) {
            total += item.getQuantity();
        }
        return total;
    }
    
    public double getTotalPrice() {
        double total = 0.0;
        for (Item item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
    
    public List<Item> getProducts() {
        return items;
    }
}
