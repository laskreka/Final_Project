/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamprinh
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import java.sql.*;

public class lectures {

    private static ArrayList<String> lecs;
    private static ArrayList<String> users;

    public static boolean existsLecture(String lecture) {
        int i;

        if (lecs == null) {
         
            lecs = new ArrayList();
            users = new ArrayList();

            return false;
        }

        for (i = 0; i < lecs.size(); i++) {
            if (lecs.get(i).equals(lecture)) {
                return true;
            }
        }

        return false;
    }

    public static String getLectures() {
        String ret;
        Connection con;
        Statement stmt;
        ResultSet rs;
        
               String ip = "localhost";

        try {
            java.net.URL url = new java.net.URL(
                    "http://agentgatech.appspot.com");

            java.net.HttpURLConnection con2 = (HttpURLConnection) url
                    .openConnection();

            java.io.InputStream stream = con2.getInputStream();

            java.io.InputStreamReader reader = new java.io.InputStreamReader(
                    stream);

            java.io.BufferedReader bReader = new java.io.BufferedReader(reader);

            ip = bReader.readLine(); //you get the IP as a String

        } catch (MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        if ( lecs == null ) {
            lecs = new ArrayList();
            users = new ArrayList();
        }

        int i;

        ret = "<b>Currently Streaming:</b><br>";
          
        for (i = 0; i < lecs.size(); i++) {
            ret = ret + "<a href=\"view?lecture=" + lecs.get(i) + "\">" + users.get(i) + ":" + lecs.get(i) + "</a><br>";
        }

        ret = ret + "<b>Archived lectures:</b><br>";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/lectures?" +
                                   "user=root&password=marouli";
            con = DriverManager.getConnection(connectionUrl);

            if (con != null) {
                System.out.println("Ola ok me mysql");
            }

            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM lectures");
            while (rs.next()) {
                ret = ret + "<a href=\"view?archive=1&lecture=" + rs.getString(2) + "\">Watch now " + rs.getString(1) + "'s lecture on " + rs.getString(2) + "</a>  ";
                ret = ret + "<a href=\"http://"+ip+":5080/oflaDemo/streams/" + rs.getString(2) + ".flv\">Download</a><br>";
            }
            con.close();
        } catch (SQLException e) {
        } catch (ClassNotFoundException e) {
        }

        return ret;
    }

    public static void startLecture(String username, String lecture) {
        if (lecs == null) {
            lecs = new ArrayList();
            users = new ArrayList();
        }
        lecs.add(lecture);
        users.add(username);
    }

    public static void stopLecture(String username, String lecture) {
        int i;
        
        if ( lecs == null ) {
            lecs = new ArrayList();
            users = new ArrayList();
        }

        for (i = 0; i < lecs.size(); i++) {
            if (lecs.get(i).equals(lecture)) {
                lecs.remove(i);
                users.remove(i);
                return;
            }
        }

    }
}
