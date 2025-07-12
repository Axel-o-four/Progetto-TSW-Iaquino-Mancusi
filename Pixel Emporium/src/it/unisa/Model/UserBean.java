package it.unisa.Model;

import java.io.Serializable;
import java.util.Date;

public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String email;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String genere;
    private String indirizzo;
    private String citta;
    private String prov;
    private String cap;
    private String password;
    private boolean admin;
    
    public UserBean() {
        this.email = "";
        this.nome = "";
        this.cognome = "";
        this.dataNascita = null;
        this.genere = "";
        this.indirizzo = "";
        this.citta = "";
        this.prov = "";
        this.cap = "";
        this.password = "";
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    public Date getDataNascita() {
        return dataNascita;
    }
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }
    
    public String getGenere() {
        return genere;
    }
    public void setGenere(String genere) {
        this.genere = genere;
    }
    
    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    
    public String getCitta() {
        return citta;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }
    
    public String getProv() {
        return prov;
    }
    public void setProv(String prov) {
        this.prov = prov;
    }
    
    public String getCap() {
        return cap;
    }
    public void setCap(String cap) {
        this.cap = cap;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isAdmin() {
    	return admin;
    }
    
    public void setAdmin(boolean admin) {
    	this.admin = admin;
    }
    
    @Override
    public String toString() {
        return "UserBean [email=" + email + ", nome=" + nome + ", cognome=" + cognome 
                + ", dataNascita=" + dataNascita + ", genere=" + genere + ", indirizzo="
                + indirizzo + ", citta=" + citta + ", prov=" + prov + ", cap=" + cap 
                + ", password=" + password + "]";
    }
}
