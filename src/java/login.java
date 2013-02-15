import javax.servlet.annotation.WebServlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

/**
 *
 * @author laskreka
 */
@WebServlet(name = "login", urlPatterns = {"/login"}, loadOnStartup=0)
public class login extends HttpServlet {
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String logout   = request.getParameter("logout");
        HttpSession session = request.getSession();
        PreparedStatement stmt;
        ResultSet rs;
        
      
        if ( logout != null ) {
            username = (String) session.getAttribute("username");
        } else {
            if ( username==null || password==null )
                return;
        }
        
        
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
            return;
        } catch ( ClassNotFoundException e ) {
            return;
        }
        try {
            if ( logout == null ) {
                stmt = con.prepareStatement("SELECT * FROM users WHERE username =? AND password =?");
                stmt.setString(1, username);
                stmt.setString(2,password);
                rs = stmt.executeQuery();
                
                if ( rs.next() ) {
                     session.setAttribute("username", username);
                     response.sendRedirect("/final_project/welcome");
                } 
                else {
                    response.sendRedirect("/final_project/login_failed.html");
                }
                rs.close();
                stmt.close();
                con.close();
            } else {
                session.invalidate();
                response.sendRedirect("/final_project/index.html");
            }
        } catch (SQLException e) {
                throw new ServletException("Servlet Could not display records.", e);
        }
        
        
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