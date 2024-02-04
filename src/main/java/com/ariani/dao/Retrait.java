package com.ariani.dao;

import java.util.Date;

public class Retrait extends Operation{
    public static final int TYPE=2;
    public Retrait() {
    }

    public Retrait(Date date, double montant, String desciption, Compte compte) {
        super(date, montant, desciption, compte);
    }
}
