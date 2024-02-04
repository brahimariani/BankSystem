package com.ariani.dao;

import java.util.Date;
import java.util.List;

public abstract class Compte {
     private int num;
     private Date dateCreation;
     protected String rib;
     private double solde;
     private Client client;
     private boolean state;
     List<Operation> operations;

    public Compte() {
    }

    public Compte(Date dateCreation, double solde, Client client) {
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.client = client;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getRIB() {
        return rib;
    }

    public void setRIB(String RIB) {
        this.rib = RIB;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
