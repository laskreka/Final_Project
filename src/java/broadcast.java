/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lamprinh
 */
public class broadcast extends HttpServlet {

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
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute("username");
        String lecture = request.getParameter("lecture");
        String doStop = request.getParameter("doStop");
        Statement stmt;
        ResultSet rs;


        if (username == null || lecture == null) {
            response.sendRedirect("welcome");
            return;
        }

        Connection con = null;
        rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/lectures?"
                    + "user=root&password=marouli";
            con = DriverManager.getConnection(connectionUrl);

            if (con != null) {
                System.out.println("Ola ok me mysql");
            } else {
                return;
            }
        } catch (SQLException e) {
            throw new ServletException("Servlet Could not display records.", e);
        } catch (ClassNotFoundException e) {
        }

        if (doStop == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String connectionUrl = "jdbc:mysql://localhost/lectures?"
                        + "user=root&password=marouli";
                con = DriverManager.getConnection(connectionUrl);

                if (con != null) {
                    System.out.println("Ola ok me mysql");
                }

                stmt = con.createStatement();

                rs = stmt.executeQuery("SELECT * FROM lectures WHERE lecture='" + lecture + "'");
                if (rs.next() || lectures.existsLecture(lecture)) {
                    out.println("<html> <head></head><body>");
                    out.println("<h1>Invalid broadcast Request, lecture exists</h1>");
                    out.println(professor.welcome(username));
                    out.println("</body></html>");
                    stmt.close();
                    rs.close();
                    con.close();
                    return;
                }
                stmt.close();
                rs.close();

                out.println(professor.broadcast(username, lecture));
            } catch (SQLException e) {
                throw new ServletException("Servlet Could not display records.", e);
            } catch (ClassNotFoundException e) {
            }
        } else {
            try {
                lectures.stopLecture(username, lecture);
                stmt = con.createStatement();

                stmt.execute("INSERT INTO lectures VALUES('" + username + "', '" + lecture + "')");
                stmt.close();
                con.close();
                response.sendRedirect("/final_project/welcome");
            } catch (SQLException e) {
                throw new ServletException("Servlet Could not display records.", e);
            }
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
