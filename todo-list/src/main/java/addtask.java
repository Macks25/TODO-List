/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zelle
 */
@WebServlet(urlPatterns = {"/addtask"})
public class addtask extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            response.addHeader("Access-Control-Allow-Origin", "*");
            String name = request.getParameter("name");
            String desc = request.getParameter("desc");
            int prio = Integer.parseInt(request.getParameter("prio"));

            Connection con;
            Statement st;
            ResultSet rs;
            PreparedStatement stmt;

            try {

                Class.forName("java.sql.Driver");
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/todoDB", "user1", "root");
                
                if(prio>0 && prio<4){
                    int num;
                Random random = new Random();
                ResultSet rs1;
                st = con.createStatement();
                            do {

                                num = random.nextInt(999999999 - 100000000) + 100000000;
                                rs1 = st.executeQuery("SELECT * FROM USER1.TODO WHERE ID = " + num);
                            } while (rs1.next());
                
                
                //INSERT INTO USER1.TODO (ID, "NAME", DESCRIPTION, CREATION, PRIO, FINISHED) VALUES (3423, 'Muell rausbringen ', 'gelb und schwarz', CURRENT_TIMESTAMP, 1, false)
                stmt = con.prepareStatement("INSERT INTO USER1.TODO (ID, \"NAME\", DESCRIPTION, CREATION, PRIO, FINISHED) VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, false)");
                stmt.setInt(1, num);
                stmt.setString(2, name);
                stmt.setString(3, desc);
                stmt.setInt(4, prio);
                
                stmt.execute();
                
                out.print(Json.createObjectBuilder().add("Finished", true).build().toString());
                }else{
                    out.print(Json.createObjectBuilder().add("Error", "Prio is not 1,2 or 3").build().toString());
                }
                
                
                

            }catch(Exception e){
                
            } 

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
