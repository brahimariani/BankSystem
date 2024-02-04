package com.ariani.servlets;

import com.ariani.connections.*;
import com.ariani.dao.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

@WebServlet({"/admin","/admin/updateclient","/admin/deleteclient",
        "/admin/showclient","/admin/closeaccount","/admin/addaccount","/admin_login","/admin/addtransaction"})
@WebFilter("/admin/*") // Specify the URL pattern you want to filter
public class AdminServlet extends HttpServlet implements Filter {

    private ClientDao clientDao;
    private CompteDao compteDao;
    private AdminDao adminDao;
    private OperationDao operationDao;

    @Override
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.clientDao = daoFactory.getClientDao();
        this.compteDao=daoFactory.getCompteDao();
        this.adminDao=daoFactory.getAdminDao();
        this.operationDao=daoFactory.getOperationDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get the requested URL
        String requestUrl = request.getRequestURI();
        if ("/BankSystem/admin".equals(requestUrl)) {
            request.setAttribute("clients",clientDao.lister());

            this.getServletContext().getRequestDispatcher("/WEB-INF/administrator/clients.jsp").forward(request, response);
        }

        if ("/BankSystem/admin/updateclient".equals(requestUrl)) {
            String cin = request.getParameter("client");

            if (cin != null && !cin.isEmpty()) {

                Client client = clientDao.getClient(cin);

                if (client != null) {
                    // Set the client as an attribute in the request
                    request.setAttribute("client", client);

                    // Forward the request to the update client form JSP page
                    this.getServletContext().getRequestDispatcher("/WEB-INF/administrator/updateclient.jsp").forward(request, response);
                } else {
                    // Handle the case when the client is not found
                    response.getWriter().println("Client not found");
                }
            } else {
                // Handle the case when the CIN parameter is missing
                response.sendRedirect("/BankSystem/admin");
            }
        }
        if ("/BankSystem/admin/showclient".equals(requestUrl)) {
            String cin = request.getParameter("client");
            if (cin != null && !cin.isEmpty()) {

                Client client = clientDao.getClient(cin);
                client.setComptes(compteDao.lister(client));

                if (client != null) {
                    // Set the client as an attribute in the request
                    request.setAttribute("client", client);

                    // Forward the request to the update client form JSP page
                    this.getServletContext().getRequestDispatcher("/WEB-INF/administrator/showclient.jsp").forward(request, response);
                } else {
                    // Handle the case when the client is not found
                    response.getWriter().println("Client not found");
                }
            } else {
                // Handle the case when the CIN parameter is missing
                response.sendRedirect("/BankSystem/admin");
            }
        }
        if ("/BankSystem/admin_login".equals(requestUrl)) {
                this.getServletContext().getRequestDispatcher("/WEB-INF/administrator/adminLogin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the requested URL
        String requestUrl = req.getRequestURI();

        if ("/BankSystem/admin".equals(requestUrl)) {
            if (req.getParameter("searchClient") != null) {
                req.setAttribute("clients", clientDao.getClients(req.getParameter("searchCleint")));
            } else if (req.getParameter("addClient") != null) {
                Client client = new Client();
                client.setNom(req.getParameter("nomClient"));
                client.setPrenom(req.getParameter("prenomClient"));
                client.setNumTel(req.getParameter("numTelClient"));
                client.setCin(req.getParameter("cinClient"));
                client.setPass(req.getParameter("passClient"));
                clientDao.ajouter(client);
                req.setAttribute("clients", clientDao.lister());
            }

            this.getServletContext().getRequestDispatcher("/WEB-INF/administrator/clients.jsp").forward(req, resp);
        }
        if ("/BankSystem/admin/updateclient".equals(requestUrl)) {
            // Retrieve form parameters
            String cin = req.getParameter("cinClient");
            String nom = req.getParameter("nomClient");
            String prenom = req.getParameter("prenomClient");
            String numTel = req.getParameter("numTelClient");

            // Perform validation if necessary

            // Assuming you have a ClientDAO to interact with the database

            Client client = clientDao.getClient(cin);

            // Update the client details
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setNumTel(numTel);

            // Update the client in the database
            clientDao.updateClient(client);

            // Redirect to a success page or the client list page
            resp.sendRedirect("/BankSystem/admin");
        }
        if ("/BankSystem/admin/addaccount".equals(requestUrl)) {

            String accountTypeStr = req.getParameter("accountType");
            String decouvertStr = req.getParameter("decouvert");
            String tauxInteretStr = req.getParameter("tauxInteret");
            String clientCIN = req.getParameter("clientCIN");

            try {
                int accountType = Integer.parseInt(accountTypeStr);

                if (accountType == 1) { // CptCourant
                    double decouvert = Double.parseDouble(decouvertStr);
                    CptCourant cptCourant = new CptCourant();
                    cptCourant.setRIB(CompteDaoImpl.generateRib());
                    cptCourant.setDecouvert(decouvert);
                    cptCourant.setDateCreation(new Date());
                    cptCourant.setSolde(0);
                    cptCourant.setClient(clientDao.getClient(clientCIN));
                    compteDao.addAccount(cptCourant);
                } else if (accountType == 2) { // CptEpargne
                    double tauxInteret = Double.parseDouble(tauxInteretStr);
                    CptEpargne cptEpargne = new CptEpargne();
                    cptEpargne.setRIB(CompteDaoImpl.generateRib());
                    cptEpargne.setTauxInteret(tauxInteret);
                    cptEpargne.setDateCreation(new Date());
                    cptEpargne.setSolde(0);
                    cptEpargne.setClient(clientDao.getClient(clientCIN));
                    compteDao.addAccount( cptEpargne);
                    System.out.println(tauxInteretStr+" "+tauxInteret);
                }
                resp.sendRedirect("/BankSystem/admin/showclient?client=" + clientCIN);
            } catch (NumberFormatException e) {
                resp.getWriter().println("Invalid input. Please enter valid numbers.");
            }
        }
        if ("/BankSystem/admin_login".equals(requestUrl)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            Admin admin =adminDao.getAdmin(username,password);
            // Replace this with your actual admin validation logic
            if (admin!=null) {
                // Admin is valid, create a session
                HttpSession session = req.getSession();
                session.setAttribute("admin", username);

                // Redirect to a welcome page or dashboard
                resp.sendRedirect( "/BankSystem/admin");
            } else {
                // Invalid credentials, redirect to login page with an error message
                resp.sendRedirect("/BankSystem/admin_login?error=true");
            }
        }



    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the requested URL
        String requestUrl = request.getRequestURI();
        System.out.println(requestUrl);
        if ("/BankSystem/admin/deleteclient".equals(requestUrl)) {
            // Get the client's CIN from the request
            String cin = request.getParameter("client");

            // Check if the CIN is not null or empty
            if (cin != null && !cin.isEmpty()) {
                // Assuming you have a ClientDAO to interact with the database
                Client client = clientDao.getClient(cin);

                if (client != null) {
                    // Delete the client from the database
                    clientDao.deleteClient(cin);
                    // Send a success response
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    // Client not found, send a not found response
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                // CIN parameter is missing, send a bad request response
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        if ("/BankSystem/admin/closeaccount".equals(requestUrl)) {
            // Get the client's CIN from the request
            String rib = request.getParameter("rib");

            // Check if the CIN is not null or empty
            if (rib != null && !rib.isEmpty()) {
                // Assuming you have a ClientDAO to interact with the database
                Compte compte = compteDao.getAccount(rib);

                if (compte != null) {
                    // Delete the Account from the database
                    compteDao.deleteAccount(compte);
                    // Send a success response
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    // Account not found, send a not found response
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                // Rib parameter is missing, send a bad request response
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if the user is logged in
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("admin") != null;

        if (isLoggedIn) {
            // User is logged in, continue with the request
            chain.doFilter(request, response);
        } else {
            // User is not logged in, redirect to the login page
            httpResponse.sendRedirect("/BankSystem/admin_login");
        }
    }
}
