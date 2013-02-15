/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamprinh
 */
import java.net.*;
import java.io.*;

public class professor {

    public static String broadcast(String username, String lecture) {
        String ret;
        String ip = "localhost";

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
        
        ret = "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n"
                    + "	<head>\n"
                    + " <title>Live Lectures</title>\n"
                    + "		<title>Recording/Broadcasting Lecture " + lecture + "</title>\n"
                    + "         <link rel=\"stylesheet\" type=\"text/css\" href=\"mystyle.css\"/>\n"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
                    + "         <script type=\"text/javascript\" src=\"assets/comments.js\"></script>\n"
                    + "		<script type=\"text/javascript\" src=\"assets/swfobject.js\"></script>\n"
                    + "		<script type=\"text/javascript\">\n"
                    + "         initialiseComments('"+lecture+"');\n"
                    + "		var flashvars = false;\n"
                    + "		var params = {\n"
                    + "		  menu: \"false\",\n"
                    + "		  flashvars: \"streamname=" + lecture + "&ip=" + ip + "&username="+username+"\"\n"
                    + "		};\n"
                    + "		swfobject.embedSWF(\"simpleBroadcaster.swf\", \"myContent\", \"800\", \"600\", \"8.0.0\", \"assets/expressInstall.swf\", flashvars, params);\n"
                    //+ "		swfobject.embedSWF(\"SimpleChat.swf\", \"myContent2\", \"400\", \"400\", \"8.0.0\", \"assets/expressInstall.swf\", flashvars, params);\n"
                    + "		</script>\n"
                    + "	</head>\n"
                    + "	<body>\n"
                    + " <div id=\"container\">"
                    + "		<div id=\"myContent\" style=\"float:left;\">\n"
                    + "			<h1>You need the Adobe Flash Player for this demo, download it by clicking the image below.</h1>\n"
                    + "			<p><a href=\"http://www.adobe.com/go/getflashplayer\"><img src=\"http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif\" alt=\"Get Adobe Flash player\" /></a></p>\n"
                    + "		</div></div>\n"
                    /*+ "		<div id=\"myContent2\">\n"
                    + "			<h1>You need the Adobe Flash Player for this demo, download it by clicking the image below.</h1>\n"
                    + "			<p><a href=\"http://www.adobe.com/go/getflashplayer\"><img src=\"http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif\" alt=\"Get Adobe Flash player\" /></a></p>\n"
                    + "		</div>\n"*/
                    + "         <a href=\"broadcast?lecture="+lecture+"&doStop=1\">Stop Broadcasting</a></br>"
                    + "<a href=\"http://"+ip+":5080/oflaDemo/streams/" + lecture + ".flv\">Download lecture</a></br>"
                    + "         <div id=\"commentbox\">"
                    + "             <div id=\"comments\">"
                    + "             </div>"
                    + "             <form action=\"#\">"
                    + "                <input type=\"hidden\" id=\"username\" value =\""+ username + "\"></br>"
                    + "                Comment: <textarea id=\"str\"></textarea></br>"
                    + "                <input type=\"button\" value =\"Post\" onclick=\"postComment()\">"
                    + "             </form>"
                    + "         </div>  "
                    + "<a href=\"welcome\""
                    + "	</body>\n"
                    + "</html>";
        lectures.startLecture(username, lecture);
        return ret;
    }

    public static String welcome(String username) {
        String ret;

        ret = "<h1 id=\"header\">" +"Welcome&nbsp;"+ username + "</h1> <a href=\"/final_project/login?logout=1\" id=\"logout\">Logout</a></br>\n";

        ret = ret + "<form method=\"post\" action=\"broadcast\">\n"
                + "            Lecture Name: <input type=\"text\" name=\"lecture\"> <br>\n"
                + "            <input type=\"submit\">\n"
                + "        </form></br>\n"
                + lectures.getLectures();
                

        return ret;
    }
}
