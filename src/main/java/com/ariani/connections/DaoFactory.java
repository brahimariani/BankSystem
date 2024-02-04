package com.ariani.connections;


import com.ariani.dao.Client;
import com.ariani.dao.Operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DaoFactory {
    private String url;
    private String username;
    private String password;
    private final ClientDaoImpl clientDao=new ClientDaoImpl(this);
    private final AdminDaoImpl adminDao=new AdminDaoImpl(this);
    private final CompteDaoImpl compteDao=new CompteDaoImpl(this);
    private final OperationDaoImpl operationDao=new OperationDaoImpl(this);

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {

        }

        DaoFactory instance = new DaoFactory(
                "jdbc:mysql://localhost:3306/BD_GC", "root", "root");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public ClientDao getClientDao() {
        return clientDao ;
    }
    public CompteDao getCompteDao() {
        return compteDao ;
    }
    public AdminDao getAdminDao(){return adminDao;}
    public OperationDao getOperationDao(){return operationDao;}
/*
    // Récupération du Dao
    public UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoImpl(this);
    }
  */
}
