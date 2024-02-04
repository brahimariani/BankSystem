package com.ariani.servlets;

import com.ariani.connections.ClientDao;
import com.ariani.connections.CompteDao;
import com.ariani.connections.DaoFactory;
import com.ariani.connections.OperationDao;
import com.ariani.dao.Admin;
import com.ariani.dao.Client;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet({"/","/client","/client_login"})
@WebFilter("/client/*") // Specify the URL pattern you want to filter
public class ClientServlet extends HttpServlet implements Filter {
    private ClientDao clientDao;
    private CompteDao compteDao;
    private OperationDao operationDao;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the requested URL
        String requestUrl = req.getRequestURI();
        if ("/BankSystem/client_login".equals(requestUrl)) {
            this.getServletContext().getRequestDispatcher("/WEB-INF/client/clientLogin.jsp").forward(req, resp);
        }
        if ("/BankSystem/client".equals(requestUrl) |"/BankSystem/".equals(requestUrl)) {
            String cin =(String) req.getSession().getAttribute("client");

            if (cin != null && !cin.isEmpty()) {

                Client client = clientDao.getClient(cin);
                client.setComptes(compteDao.lister(client));

                if (client != null) {
                    // Set the client as an attribute in the request
                    req.setAttribute("client", client);

                    // Forward the request to the update client form JSP page
                    this.getServletContext().getRequestDispatcher("/WEB-INF/client/main.jsp").forward(req, resp);
                } else {
                    // Handle the case when the client is not found
                    resp.getWriter().println("Client not found");
                }
            } else {
                // Handle the case when the CIN parameter is missing
                resp.sendRedirect("/BankSystem/client_login");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestUrl = req.getRequestURI();
        if ("/BankSystem/client_login".equals(requestUrl)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            Client client = clientDao.getClient(username, password);
            // Replace this with your actual admin validation logic
            if (client != null) {
                // Admin is valid, create a session
                HttpSession session = req.getSession();
                session.setAttribute("client" , username);
                // Redirect to a welcome page or dashboard
                //resp.sendRedirect("/BankSystem/client");
                resp.getWriter().write("success");
            } else {
                // Invalid credentials, redirect to login page with an error message
                resp.getWriter().write("error");
            }
        }
    }

    @Override
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.clientDao = daoFactory.getClientDao();
        this.compteDao=daoFactory.getCompteDao();
        this.operationDao=daoFactory.getOperationDao();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request ;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Check if the user is logged in
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("client") != null;

        if (isLoggedIn) {
            // User is logged in, continue with the request
            chain.doFilter(request, response);
        } else {
            // User is not logged in, redirect to the login page
            httpResponse.sendRedirect("/BankSystem/client_login");
        }
    }

}
