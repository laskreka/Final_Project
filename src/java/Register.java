
import javax.servlet.annotation.WebServlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 *
 * @author laskreka
 */
@WebServlet(name = "Register", urlPatterns = {"/register"}, loadOnStartup = 0)
public class Register extends HttpServlet {

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
        PreparedStatement stmt;
        ResultSet rs;


        if (username == null || password == null) {
            out.println("<h1>Invalid Register Request</h1>");
            out.println("<a href=\"register.html\">Register</a>");

            return;
        }

        Connection con;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/lectures?"
                    + "user=root&password=marouli";
            con = DriverManager.getConnection(connectionUrl);

            if (con != null) {
                System.out.println("Ola ok me mysql");
            }

            stmt = con.prepareStatement("SELECT * FROM users WHERE username =?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                response.sendRedirect("/final_project/register_failed.html");

                stmt.close();
                rs.close();
                con.close();
                return;
            }
            stmt.close();
            rs.close();


            stmt = con.prepareStatement("insert into users VALUES(?,?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            
            out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"welcome.css\"></head>");
            out.println("<body><div id=\"main_content\">");
            out.println("<h1>You are now registered " + username + "</h1>");
            out.println("<a href=\"login.html\">Login</a>");
            out.println("</div></body>");
            out.println("</html>");

            stmt.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            throw new ServletException("Servlet Could not display records.", e);
        } catch (ClassNotFoundException e) {
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