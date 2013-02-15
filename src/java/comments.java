/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marouli
 */
public class comments extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String lecture  = request.getParameter("lecture");
        String str = request.getParameter("str");
        String id = request.getParameter("id");
        
        PreparedStatement stmt;
        ResultSet rs;       
        Connection con=null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/lectures?" +
                                   "user=root&password=marouli";
            con = DriverManager.getConnection(connectionUrl);

            if ( con!=null ) {
               System.out.println("Ola ok me mysql");
            }
        } catch( SQLException e ) {
            out.println("Connection to SQL failed");
        } catch ( ClassNotFoundException e ) {
        }
        
        
        if ( action.equals("add") && username != null  && lecture !=null && str != null) {
            try {
                stmt = con.prepareStatement("insert into comments (username, lecture, str )  VALUES(?,?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, lecture);
                stmt.setString(3, str);
                stmt.executeUpdate();
           } catch (SQLException e ) {
               
           } finally {
           } 
          
        } else if ( action.equals("list") && id != null ) {
            try {
                Statement stmt2 = con.createStatement();
                rs = stmt2.executeQuery("select username, str, id  from comments where id > " 
                        + id + " and lecture='" + lecture + "'");
                Integer max  = new Integer(id);
                String ret = "";
                
                while ( rs.next() ) {
                    if ( max < rs.getInt(3))
                        max = rs.getInt(3);
                    
                    ret = ret + "<div class=\"comment\">\n";
                    ret = ret + "\t<p class=\"username\">"+rs.getString(1)+"</p>\n";
                    ret = ret + "\t<p class=\"str\">"+rs.getString(2)+"</p>\n";
                    ret = ret + "</div>";
                }
                if ( max.equals (new Integer(id)) == false )
                    ret = max + "&" + ret;
                
                
                out.println(ret);
            } catch ( SQLException e ) {
            }
        }
        out.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
