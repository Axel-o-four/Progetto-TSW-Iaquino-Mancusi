package it.unisa.Model;

import java.io.Serializable;
import java.sql.Date;

public class OrdineBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String emailUtente;
    private Date dataOrdine;
    private int quantita;
    private double importo;
    private double iva;
    private double totaleIva;
    private double totaleFattura;
    private String paese;
    private String citta;
    private String cap;
    private String provincia;
    private String via;
    private String numeroCivico;
    private char tipoPagamento;
    private String numeroCarta;
    private Date scadenzaCarta;
    private String cvv;
    private String emailPaypal;

    public OrdineBean() {
        this.id = -1;
        this.emailUtente = "";
        this.dataOrdine = null;
        this.quantita = 0;
        this.importo = 0.0;
        this.iva = 0.0;
        this.totaleIva = 0.0;
        this.totaleFattura = 0.0;
        this.paese = "";
        this.citta = "";
        this.cap = "";
        this.provincia = "";
        this.via = "";
        this.numeroCivico = "";
        this.tipoPagamento = ' ';
        this.numeroCarta = "";
        this.scadenzaCarta = null;
        this.cvv = "";
        this.emailPaypal = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public Date getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Date dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotaleIva() {
        return totaleIva;
    }

    public void setTotaleIva(double totaleIva) {
        this.totaleIva = totaleIva;
    }

    public double getTotaleFattura() {
        return totaleFattura;
    }

    public void setTotaleFattura(double totaleFattura) {
        this.totaleFattura = totaleFattura;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public char getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(char tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public Date getScadenzaCarta() {
        return scadenzaCarta;
    }

    public void setScadenzaCarta(Date scadenzaCarta) {
        this.scadenzaCarta = scadenzaCarta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getEmailPaypal() {
        return emailPaypal;
    }

    public void setEmailPaypal(String emailPaypal) {
        this.emailPaypal = emailPaypal;
    }

    @Override
    public String toString() {
        return "OrdineBean [id=" + id + ", emailUtente=" + emailUtente + ", dataOrdine=" + dataOrdine
                + ", quantita=" + quantita + ", importo=" + importo + ", iva=" + iva
                + ", totaleIva=" + totaleIva + ", totaleFattura=" + totaleFattura + ", paese=" + paese
                + ", citta=" + citta + ", cap=" + cap + ", provincia=" + provincia + ", via=" + via
                + ", numeroCivico=" + numeroCivico + ", tipoPagamento=" + tipoPagamento
                + ", numeroCarta=" + numeroCarta + ", scadenzaCarta=" + scadenzaCarta + ", cvv=" + cvv
                + ", emailPaypal=" + emailPaypal + "]";
    }
}
