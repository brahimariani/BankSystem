package com.ariani.connections;
import com.ariani.dao.Client;
import com.ariani.dao.Compte;

import java.util.List;



public interface ClientDao {
    void ajouter( Client client );
    List<Client> lister();
    List<Client> getClients(String by);
   Client getClient(String by);
    Client getClient(String cin,String pass);
   void deleteClient(String by);
   void updateClient(Client client);

}
