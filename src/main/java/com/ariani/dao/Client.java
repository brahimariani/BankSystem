package com.ariani.dao;

import java.io.Serializable;
import java.util.List;

public class Client implements Serializable {
    private int code;
    private String nom,prenom,numTel,cin,pass;
    private List<Compte> comptes;

    public Client() {
    }

    public Client(String cin,String nom, String prenom, String numTel,String pass) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.cin=cin;
        this.pass=pass;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
