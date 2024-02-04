package com.ariani.dao;

import java.util.Date;
import java.util.List;

public class CptEpargne extends Compte{


    public static final int TYPE=2;
    private double tauxInteret;

    private List<Operation> operations;

    public CptEpargne() {
        super();
    }

    public CptEpargne(int tauxInteret, List<Operation> operations) {
        this.tauxInteret = tauxInteret;
        this.operations = operations;
    }

    public CptEpargne(Date dateCreation, double solde, Client client, int tauxInteret, List<Operation> operations) {
        super(dateCreation, solde, client);
        this.tauxInteret = tauxInteret;
        this.operations = operations;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
