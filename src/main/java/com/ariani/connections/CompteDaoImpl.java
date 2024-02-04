package com.ariani.connections;



import com.ariani.dao.Client;
import com.ariani.dao.Compte;
import com.ariani.dao.CptCourant;
import com.ariani.dao.CptEpargne;

import java.security.cert.CertPathBuilder;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CompteDaoImpl implements CompteDao {
    private DaoFactory daoFactory;

    CompteDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addAccount(Compte compte) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO compte(dateCreation,rib, typeCompte,decouvert,tauxInteret,codeClient) VALUES(?,?, ?,?,?,?);");
            preparedStatement.setDate(1, new Date(compte.getDateCreation().getTime()));
            preparedStatement.setString(2, compte.getRIB());
            if(compte instanceof CptCourant){
                preparedStatement.setInt(3, CptCourant.TYPE);
                preparedStatement.setDouble(4, ((CptCourant) compte).getDecouvert());
                preparedStatement.setNull(5,Types.INTEGER);
            } else if (compte instanceof CptEpargne) {
                preparedStatement.setInt(3, CptEpargne.TYPE);
                preparedStatement.setNull(4,Types.INTEGER);
                preparedStatement.setDouble(5, ((CptEpargne) compte).getTauxInteret());
            }else {
                return;
            }
            preparedStatement.setInt(6, compte.getClient().getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } 

    }

    @Override
    public List<Compte> lister(Client client) {
        List<Compte> comptes = new ArrayList<Compte>();
        Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM compte where codeClient=? and state<>0;");
            statement.setInt(1,client.getCode());
            resultat = statement.executeQuery();

            while (resultat.next()) {
                int num = resultat.getInt("numCompte");
                String rib = resultat.getString("rib");
                Date dateCreation = resultat.getDate("dateCreation");
                double solde = resultat.getDouble("solde");
                int type = resultat.getInt("typeCompte");
                double decouvert = resultat.getDouble("decouvert");
                double tauxInteret = resultat.getDouble("tauxInteret");



                if(type==CptCourant.TYPE) {
                    CptCourant compte = new CptCourant();
                    compte.setNum(num);
                    compte.setRIB(rib);
                    compte.setDateCreation(dateCreation);
                    compte.setSolde(solde);
                    compte.setDecouvert(decouvert);
                    compte.setClient(client);
                    comptes.add(compte);
                }
                if(type==CptEpargne.TYPE) {
                    CptEpargne compte = new CptEpargne();
                    compte.setNum(num);
                    compte.setRIB(rib);
                    compte.setDateCreation(dateCreation);
                    compte.setSolde(solde);
                    compte.setTauxInteret(tauxInteret);
                    compte.setClient(client);
                    comptes.add(compte);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comptes;
    }
    public static String generateRib() {
        // RIB consists of 24 digits
        StringBuilder ribBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            int digit = random.nextInt(10);
            ribBuilder.append(digit);
        }

        return ribBuilder.toString();
    }
    @Override
    public Compte getAccount(String Byrib) {
        Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;
        Compte compte=null;
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM compte where rib=? and state<>0;");
            statement.setString(1,Byrib);
            resultat = statement.executeQuery();

            if (resultat.next()) {
                int num = resultat.getInt("numCompte");
                String rib = resultat.getString("rib");
                Date dateCreation = resultat.getDate("dateCreation");
                double solde = resultat.getDouble("solde");
                int type = resultat.getInt("typeCompte");
                double decouvert = resultat.getDouble("decouvert");
                double tauxInteret = resultat.getDouble("tauxInteret");



                if(type==CptCourant.TYPE) {
                    CptCourant c1 = new CptCourant();
                    c1.setNum(num);
                    c1.setRIB(rib);
                    c1.setDateCreation(dateCreation);
                    c1.setSolde(solde);
                    c1.setDecouvert(decouvert);
                    compte=c1;
                }
                if(type==CptEpargne.TYPE) {
                    CptEpargne c2 = new CptEpargne();
                    c2.setNum(num);
                    c2.setRIB(rib);
                    c2.setDateCreation(dateCreation);
                    c2.setSolde(solde);
                    c2.setTauxInteret(tauxInteret);
                    compte=c2;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return compte;
        }
        return compte;
    }
@Override
    public void deleteAccount(Compte compte) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement("UPDATE  compte set state=0 WHERE rib=?");
            statement.setString(1, compte.getRIB());

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

}
