/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author j45-martin
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
        JDBCBean bean = (JDBCBean) getServletContext().getAttribute("JDBCBean");
        String errorMessage = "";
        
            RequestDispatcher view = request.getRequestDispatcher("Register.jsp");
            view.forward(request, response);
            
        String fullName = request.getParameter("fullName");
        //generating username
        char initial = fullName.charAt(0);
        String[] names = fullName.split(" ");
        //Check if full name is entered
        if (names.length < 2) {
            errorMessage = "<div class=\"error\">\n"
                    + "  <span class=\"errorMessageBtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span> \n"
                    + "  <strong>Full Name Required!</strong> \n"
                    + "</div>";
            request.setAttribute("ErrorMessage", errorMessage);

        } else {
            String username = (initial + "-" + names[1]).toLowerCase();
//            String streetNumber = request.getParameter("streetNumber");
//            String streetName = request.getParameter("streetName");
//            String city = request.getParameter("city");
//            String postcode = request.getParameter("postcode");
//            String country = request.getParameter("country");

//            String address = streetNumber + ", " + streetName + ", " + city + ", " + postcode + ", " + country;
            String address = request.getParameter("address");
            String dob = request.getParameter("DOB");
            //DOR to current date in YYYY-MM-DD format
            SimpleDateFormat sqlDateFormatForRegistration = new SimpleDateFormat("yyyy-MM-dd");
            String dor = sqlDateFormatForRegistration.format(Calendar.getInstance().getTime());
            //password in DDMMYY format
            String password = request.getParameter("DOB").replaceAll("(..)(..)-(..)-(..)", "$4$3$2");
            float RegistrationFee = 10;
            String defaultStatus = "APPLIED";


            bean.executeSQLUpdate("INSERT INTO `Members`(`id`, `name`, `address`, `dob`, `dor`, `status`, `balance`)"
                    + "VALUES (" + "'" + username + "','" + fullName + "','" + address + "','" + dob + "','" + dor + "','" + defaultStatus + "'," + RegistrationFee + ")");
            bean.executeSQLUpdate("INSERT INTO `users`(`id`, `password`, `status`)"
                    + "VALUES (" + "'" + username + "','" + password + "','" + defaultStatus + "'" + ")");
            
            request.getRequestDispatcher("RegistrationSuccessful.jsp").forward(request, response);
            request.setAttribute("registeredUsername", username);
            request.setAttribute("registeredUsernamePassword", password);

        }       

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
        processRequest(request, response);
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
