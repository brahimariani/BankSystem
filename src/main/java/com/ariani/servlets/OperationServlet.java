package com.ariani.servlets;

import com.ariani.connections.*;
import com.ariani.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

@WebServlet({"/client/showaccount"})
public class OperationServlet extends HttpServlet {
    private ClientDao clientDao;
    private CompteDao compteDao;
    private OperationDao operationDao;
    @Override
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.clientDao = daoFactory.getClientDao();
        this.compteDao=daoFactory.getCompteDao();
        this.operationDao=daoFactory.getOperationDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = req.getRequestURI();

        if ("/BankSystem/client/showaccount".equals(requestUrl)) {
            HttpSession session = req.getSession();
            Client client = clientDao.getClient((String) session.getAttribute("client"));
            Compte compte = compteDao.getAccount(req.getParameter("rib"));
            compte.setOperations(operationDao.lister(compte));
            req.setAttribute("client", client);
            req.setAttribute("compte", compte);
            // Forward the request to the update client form JSP page
            this.getServletContext().getRequestDispatcher("/WEB-INF/client/showaccount.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result="error";
        String requestUrl = req.getRequestURI();
            HttpSession session = req.getSession();
            Compte compte = compteDao.getAccount(req.getParameter("account"));
            int transactionType=Integer.valueOf(req.getParameter("transactionType"));
            double amount=Double.valueOf(req.getParameter("montant"));
            String description=req.getParameter("description");
            if(transactionType== Retrait.TYPE && compte.getSolde()<amount){
                result="amount";
            }else{
                if (transactionType== Depot.TYPE) {
                    Operation operation =new Depot();
                    operation.setDate(new Date());
                    operation.setCompte(compte);
                    operation.setDesciption(description);
                    operation.setMontant(amount);
                    if(operationDao.ajouter(operation) && operationDao.updateAccount(operation)){
                        result="success";
                    }else {
                        result="error";

                    }
                }
                if (transactionType== Retrait.TYPE) {
                    if (compte instanceof CptCourant) {
                        Operation operation = new Retrait();
                        operation.setDate(new Date());
                        operation.setCompte(compte);
                        operation.setDesciption(description);
                        operation.setMontant(amount);
                        if (operationDao.ajouter(operation) && operationDao.updateAccount(operation)) {
                            result = "success";
                        } else {
                            result = "error";
                        }
                    }else{
                        result = "epargne";
                    }
                }
            }

        resp.getWriter().write(result);
    }
}
