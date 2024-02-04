package com.ariani.dao;

import java.util.Date;
import java.util.List;

public class CptCourant extends Compte{
    public static final int TYPE=1;
    private double decouvert;

    private List<Operation> operations;

    public CptCourant() {
        super();
    }

    public CptCourant(int decouvert, List<Operation> operations) {
        this.decouvert = decouvert;
        this.operations = operations;
    }

    public CptCourant(Date dateCreation, double solde, Client client, int decouvert, List<Operation> operations) {
        super(dateCreation, solde, client);
        this.decouvert = decouvert;
        this.operations = operations;
    }

    public double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(double decouvert) {
        this.decouvert = decouvert;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
