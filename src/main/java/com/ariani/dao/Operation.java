package com.ariani.dao;

import java.util.Date;

public abstract class Operation {
    private int code;
    private Date date;
    private double montant;
    private String description;
    private Compte compte;

    public Operation() {
    }

    public Operation(Date date, double montant, String desciption, Compte compte) {
        this.date = date;
        this.montant = montant;
        this.description = desciption;
        this.compte = compte;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDesciption() {
        return description;
    }

    public void setDesciption(String desciption) {
        this.description = desciption;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
