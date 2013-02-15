/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lamprinh
 */
public class view extends HttpServlet {

    private static Integer viewer = 0;

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
        String archive = request.getParameter("archive");

        String ret;
        String ip = "localhost";
       
        if (username == null) {
            response.sendRedirect("index.html");
            return;
        }
        
        try {
            java.net.URL url = new java.net.URL(
                    "http://agentgatech.appspot.com");

            java.net.HttpURLConnection con = (HttpURLConnection) url
                    .openConnection();

            java.io.InputStream stream = con.getInputStream();

            java.io.InputStreamReader reader = new java.io.InputStreamReader(
                    stream);

            java.io.BufferedReader bReader = new java.io.BufferedReader(reader);

            ip = bReader.readLine(); //you get the IP as a String

        } catch (MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        
        
        try {
            /* TODO output your page here. You may use following sample code. */
            viewer = viewer + 1;

            out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n"
                    + "	<head>\n"
                    + "		<title>Watching Lecture " + lecture + "</title>\n"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                    + "         <link rel=\"stylesheet\" type=\"text/css\" href=\"mystyle.css\"/>\n"
                    + "         <script type=\"text/javascript\" src=\"assets/comments.js\"></script>\n"
                    + "		<script type=\"text/javascript\" src=\"assets/swfobject.js\"></script>\n"
                    + "         <script type=\"text/javascript\" src=\"jwplayer.js\"></script>\n"
                    + "		<script type=\"text/javascript\">\n"
                    + "         initialiseComments('" + lecture + "');\n");

            out.println(
                    //+ "		swfobject.embedSWF(\"SimpleChat.swf\", \"myContent2\", \"400\", \"400\", \"8.0.0\", \"assets/expressInstall.swf\", flashvars, params);\n"
                    " </script>\n"
                    + "	</head>\n"
                    + "	<body>\n"
                    + " <div id=\"container\">"
                    + "		<div id=\"myContent\" style=\"float:left;\">\n");
            out.println("		</div></div>\n");
            out.println("<script type='text/javascript'>\n"
                    + "  jwplayer('myContent').setup({\n"
                    + "    'flashplayer': 'player.swf',\n");
                    if ( archive == null ) {
                        out.println("'file': '"+lecture+"',\n");
                        out.println("'rtm.subscribe': 'true',\n");
                    } else {
                        out.println("'file': '"+lecture+".flv',\n");
                    }
            out.println( "    'streamer': 'rtmp://"+ip+"/oflaDemo',\n"
                    + "    'controlbar': 'bottom',\n"
                    + "    'width': '800',\n"
                    + "    'height': '600'\n"
                    + "  });\n"
                    + "</script>");


            /*+ "		<div id=\"myContent2\">\n"
             + "			<h1>You need the Adobe Flash Player for this demo, download it by clicking the image below.</h1>\n"
             + "			<p><a href=\"http://www.adobe.com/go/getflashplayer\"><img src=\"http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif\" alt=\"Get Adobe Flash player\" /></a></p>\n"
             + "		</div>\n" */
            out.println("          <div class=\"neo5\">\n"
                    + "         <a href=\"welcome\">Go Back</a></br>\n"
                    + "          </div>\n"
                    + "          <a href=\"http://" + ip + ":5080/oflaDemo/streams/" + lecture + ".flv\">Download lecture</a></br>"
                    + "         <div id=\"commentbox\">\n"
                    + "             <div id=\"comments\">\n"
                    + "             </div>\n"
                    + "             <form action=\"#\">\n"
                    + "                <input type=\"hidden\" id=\"username\" value =\"" + username + "\"></br>"
                    + "                Comment:</br> <textarea id=\"str\"></textarea></br>"
                    + "                <input type=\"button\" value =\"Post\" onclick=\"postComment()\">"
                    + "             </form>"
                    + "         </div>  "
                    + "	</body>\n"
                    + "</html>");
        } finally {
            out.close();
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
