package it.unisa.Model;

import java.io.Serializable;

public class DettaglioOrdineBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int orderId;
    private String emailUtente;
    private char tipoProdotto;
    private int idProdotto;
    private int quantita;
    private double prezzoUnitario;
    
    private String nome;
    private String immagine;
    private String descrizione;
    
    public DettaglioOrdineBean() { }
    
    
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public String getEmailUtente() {
        return emailUtente;
    }
    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }
    public char getTipoProdotto() {
        return tipoProdotto;
    }
    public void setTipoProdotto(char tipoProdotto) {
        this.tipoProdotto = tipoProdotto;
    }
    public int getIdProdotto() {
        return idProdotto;
    }
    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }
    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }
    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getImmagine() {
        return immagine;
    }
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
