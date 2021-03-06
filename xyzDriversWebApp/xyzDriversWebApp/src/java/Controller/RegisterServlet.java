/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import model.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Frazer Pinheiro
 */
public class RegisterServlet extends HttpServlet {

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
        RequestDispatcher view = request.getRequestDispatcher("Register.jsp");
        view.forward(request, response);

    }

    public void registerMember(String[] info) throws SQLException {
        //String user = "root";
        Connection con = null;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection(DBConnection.HOST, DBConnection.USER, DBConnection.PASS);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String name = info[0];
        String addr = info[1];
        String dob = info[2];
        LocalDate dor = LocalDate.now();
        String status = "APPLIED";
        double balance = 10;

        String id = generateID(name).toLowerCase();

        String members = "INSERT INTO ROOT.MEMBERS VALUES ('" + id + "','" + name.trim() + "','" + addr.trim() + "','" + dob + "','" + dor + "','" + status + "'," + balance + ")";
        String users = "INSERT INTO ROOT.USERS VALUES ('" + id + "','" + generatePassword(dob) + "','" + status + "')";

        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate(members);
            state.executeUpdate(users);

        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String generateID(String name) {
        StringBuilder id = new StringBuilder();

        id.append(name.charAt(0)).append("-");
        int space = 0;

        try {
            space = name.indexOf(' ');
        } catch (Exception e) {

        }

        for (int i = space + 1; i < name.length(); i++) {
            id.append(name.charAt(i));
        }

        return id.toString();
    }

    public String generatePassword(String dob) {

        dob = dob.replaceAll("-", "");

        StringBuilder password = new StringBuilder(dob.substring(2)).reverse();

        return password.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] info = new String[3];
        info[0] = request.getParameter("fullName").trim();// + " " + request.getParameter("lname").trim();
        info[1] = request.getParameter("address");//+ ", " + request.getParameter("addr2") + ", " + request.getParameter("addr3") + ", " + request.getParameter("addr4");
        info[2] = request.getParameter("dob");

        System.out.println("name: " + info[0]);
        System.out.println("address: " + info[1]);
        System.out.println("dob: " + info[2]);

        try {
            registerMember(info);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestDispatcher view = request.getRequestDispatcher("RegistrationSuccessful.jsp");
        view.forward(request, response);

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
