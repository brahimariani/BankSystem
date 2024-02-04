package com.ariani.connections;

import com.ariani.dao.Client;
import com.ariani.dao.Compte;

import java.util.List;

public interface CompteDao {
    void addAccount( Compte compte );
    List<Compte> lister(Client client);
    Compte getAccount(String rib);
    void deleteAccount(Compte compte);
}
