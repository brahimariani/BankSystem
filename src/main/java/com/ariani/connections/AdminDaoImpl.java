package com.ariani.connections;

import com.ariani.dao.Admin;
import com.ariani.dao.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDao{
    private DaoFactory daoFactory;

    AdminDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

@Override
    public Admin getAdmin(String name,String password) {
        Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;
        Admin admin=null;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM admin where name=? and password=? ;");
            statement.setString(1,name);
            statement.setString(2,password);
            resultat = statement.executeQuery();

            
            if (resultat.next()) {
                String name1 = resultat.getString("name");
                String password1 = resultat.getString("password");
                admin=new Admin();
                admin.setName(name1);
                admin.setPassword(password1);
                
            }
        } catch (SQLException e) {
            return null;
        }
        return admin;
    }


}
