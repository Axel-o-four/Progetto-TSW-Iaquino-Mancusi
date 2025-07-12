package it.unisa.Model;

public interface Item {
    String getPrefissoId();
	int getCode();
    String getName();
    int getQuantity();
    void setQuantity(int quantity);
    double getPrice();
}
