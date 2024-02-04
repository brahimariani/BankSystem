package com.ariani.dao;

import java.util.Date;

public class Depot extends Operation{
    public static final int TYPE=1;
    public Depot() {
        super();
    }

    public Depot(Date date, double montant, String desciption, Compte compte) {
        super(date, montant, desciption, compte);
    }
}
