package com.ariani.connections;

import com.ariani.dao.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao{
    private DaoFactory daoFactory;

    ClientDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    @Override
    public void ajouter(Client client) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO client(cinClient,nomClient, prenomClient,numTelClient,passClient) VALUES(?,?, ?,?,?);");
            preparedStatement.setString(1,client.getCin());
            preparedStatement.setString(2,client.getNom());
            preparedStatement.setString(3,client.getPrenom());
            preparedStatement.setString(4, client.getNumTel());
            preparedStatement.setString(5, client.getPass());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> lister() {
        List<Client> clients = new ArrayList<>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM client;");

            while (resultat.next()) {
                int code = resultat.getInt("codeClient");
                String nom = resultat.getString("nomClient");
                String prenom = resultat.getString("prenomClient");
                String numTel =resultat.getString("numTelClient");
                String cin =resultat.getString("cinClient");
                String pass =resultat.getString("passClient");


                Client client = new Client();
                client.setCode(code);
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setNumTel(numTel);
                client.setCin(cin);
                client.setPass(pass);
                clients.add(client);


            }
        } catch (SQLException e) {
            return  new ArrayList<>();
        }
        return clients;
    }

    @Override
    public List<Client> getClients(String by) {
        List<Client> clients=new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM client where codeClient=? or nomClient=? or prenomClient=? or cinClient=?;");
            statement.setString(1,by);
            statement.setString(2,by);
            statement.setString(3,by);
            statement.setString(4,by);
            resultat = statement.executeQuery();

            while (resultat.next()) {
                int code = resultat.getInt("codeClient");
                String nom = resultat.getString("nomClient");
                String prenom = resultat.getString("prenomClient");
                String numTel =resultat.getString("numTelClient");
                String cin =resultat.getString("cinClient");
                String pass =resultat.getString("passClient");
                Client client = new Client();
                client.setCode(code);
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setNumTel(numTel);
                client.setCin(cin);
                client.setPass(pass);
                clients.add(client);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return clients;
    }
    public Client getClient(String by) {
        Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;
        Client client = null;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM client where codeClient=? or nomClient=? or prenomClient=? or cinClient=?;");
            statement.setString(1,by);
            statement.setString(2,by);
            statement.setString(3,by);
            statement.setString(4,by);
            resultat = statement.executeQuery();

            
            if (resultat.next()) {
                int code = resultat.getInt("codeClient");
                String nom = resultat.getString("nomClient");
                String prenom = resultat.getString("prenomClient");
                String numTel =resultat.getString("numTelClient");
                String cin =resultat.getString("cinClient");
                String pass =resultat.getString("passClient");
                client = new Client();
                client.setCode(code);
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setNumTel(numTel);
                client.setCin(cin);
                client.setPass(pass);
                
            }
        } catch (SQLException e) {
            return null;
        }
        return client;
    }
    public Client getClient(String cin, String password) {
        Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;
        Client client=null;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM client where cinClient=? and passClient=? ;");
            statement.setString(1,cin);
            statement.setString(2,password);
            resultat = statement.executeQuery();


            if (resultat.next()) {

                int code = resultat.getInt("codeClient");
                String nom = resultat.getString("nomClient");
                String prenom = resultat.getString("prenomClient");
                String numTel =resultat.getString("numTelClient");
                String cinClient =resultat.getString("cinClient");
                String pass =resultat.getString("passClient");
                client = new Client();
                client.setCode(code);
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setNumTel(numTel);
                client.setCin(cinClient);
                client.setPass(pass);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return client;
    }
    public void deleteClient(String by) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement("DELETE FROM client WHERE cinClient=?");
            statement.setString(1, by);

            // Execute the delete query
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        } finally {
            // Close resources in the reverse order of their creation
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Handle exceptions appropriately
                e.printStackTrace();
            }
        }
    }
    public void updateClient(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(
                    "UPDATE client SET nomClient=?, prenomClient=?, numTelClient=? WHERE cinClient=?");

            // Set parameters for the UPDATE query
            statement.setString(1, client.getNom());
            statement.setString(2, client.getPrenom());
            statement.setString(3, client.getNumTel());
            statement.setString(4, client.getCin());

            // Execute the update query
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        } finally {
            // Close resources in the reverse order of their creation
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Handle exceptions appropriately
                e.printStackTrace();
            }
        }
    }
}
