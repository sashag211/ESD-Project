/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.sql.*;
import java.util.logging.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author j45-martin
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        ServletContext sc = request.getServletContext();
        Connection conn = (Connection) sc.getAttribute("connection");
        HttpSession session = request.getSession();
        RequestDispatcher view;
        User user = (User) session.getAttribute("user");
        
        request.getRequestDispatcher("/Login.jsp").forward(request, response);
        response.sendRedirect(sc.getContextPath() + "/userDashboard.jsp");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext sc = request.getServletContext();
        
        //creates a session
        HttpSession session = request.getSession();

        response.setContentType("text/html;charset=UTF-8");
        Connection conn = (Connection) sc.getAttribute("connection");

        //take values from login.jsp
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = null;

        try {
            user = User.getUser(conn, username); //retrieve user from database if they exist
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

//        if (user != null && user.getPassword().equals(password)) { //check user exists            
//            session.setAttribute("user", user);
//            response.sendRedirect(sc.getContextPath() + "/userDashboard");
//        } else { //user didn't exist, reload page with error message.
//            session.setAttribute("error", "Invalid Login");
//            response.sendRedirect(sc.getContextPath());
//        }
        response.sendRedirect(sc.getContextPath() + "/userDashboard");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
