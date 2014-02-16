/****************
 *Steven Bartfield and Rehan Balagamwala- MPCS 54001 - Project 1
 ****************/

//code snips from http://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoServer.java

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.text.DateFormat;

public class HTTPServer {
    public static void main(String[] args) throws Exception {
        //tracks to make sure correct number of arguments
        if (args.length != 1) {
            System.out.println("Not correct amount of args - <port number>");
            return;  // end program, not correct amount of args
        }

        //parse out the port number
        String[] arrInput = args[0].split("=");
	Date dt = new Date();
	String datetime = dt.toString();

        //opens a socket at the given port number - opens up a reader and writer
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(arrInput[1]));
        Socket clientSocket = serverSocket.accept();
        PrintWriter wOut = new PrintWriter(clientSocket.getOutputStream(), true);
	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


	out.write("HTTP/1.0 200 OK\r\n");
	out.write(datetime+"\r\n");
	out.write("Server: Apache/0.8.4\r\n");
	out.write("Content-Type: text/html\r\n");
	out.write("\r\n");
	out.write("<TITLE>This is working</TITLE>");
	out.write("<P>Received message.</P>");

	System.err.println("Connection terminated");
	out.close();
	in.close();
	clientSocket.close();	




    }
}
