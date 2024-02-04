package com.ariani.connections;

import com.ariani.dao.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDaoImpl implements OperationDao{
    private DaoFactory daoFactory;

    public OperationDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean ajouter(Operation operation) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        boolean res=false;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO operation(dateOperation, montant,desOperation,typeOperation,numCompte) VALUES(?, ?,?,?,?);");
            preparedStatement.setDate(1, new Date(operation.getDate().getTime()));
            preparedStatement.setDouble(2, operation.getMontant());
            preparedStatement.setString(3,operation.getDesciption());

            if(operation instanceof Depot){
                preparedStatement.setInt(4, Depot.TYPE);
            } else if (operation instanceof Retrait) {
                preparedStatement.setInt(4, Retrait.TYPE);
            }
            preparedStatement.setInt(5, operation.getCompte().getNum());
            res= preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    @Override
    public boolean updateAccount(Operation operation){
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        boolean flag=false;
        try {
            connexion = daoFactory.getConnection();
            if(operation instanceof Depot){
                preparedStatement = connexion.prepareStatement("UPDATE compte set solde=solde+? where rib=?");
            } else if (operation instanceof Retrait) {
                preparedStatement = connexion.prepareStatement("UPDATE compte set solde=solde-? where rib=?");
            }else{
                return flag;
            }
            preparedStatement.setDouble(1,operation.getMontant());
            preparedStatement.setString(2, operation.getCompte().getRIB());

            if(preparedStatement.executeUpdate()>0)
                flag=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Operation> lister(Compte compte) {
        List<Operation> operations = new ArrayList<Operation>();
        Connection connexion = null;
        PreparedStatement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM operation where numCompte=?;");
            statement.setInt(1,compte.getNum());
            resultat = statement.executeQuery();

            while (resultat.next()) {
                int num = resultat.getInt("codeOperation");
                Date dateOperation = resultat.getDate("dateOperation");
                double montant = resultat.getDouble("montant");
                int type = resultat.getInt("typeOperation");
                String description = resultat.getString("desOperation");



                if(type== Depot.TYPE) {
                    Depot depot=new Depot();
                    depot.setCode(num);
                    depot.setDate(dateOperation);
                    depot.setMontant(montant);
                    depot.setDesciption(description);
                    depot.setCompte(compte);
                    operations.add(depot);
                }
                if(type== Retrait.TYPE) {
                    Retrait retrait=new Retrait();
                    retrait.setCode(num);
                    retrait.setDate(dateOperation);
                    retrait.setMontant(montant);
                    retrait.setDesciption(description);
                    retrait.setCompte(compte);
                    operations.add(retrait);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operations;
    }
}
