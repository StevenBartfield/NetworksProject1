/****************
 *Steven Bartfield - MPCS 54001 - Project 0
 ****************/

//code snips from http://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoServer.java

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEcho {
    public static void main(String[] args) throws Exception {
        //tracks to make sure correct number of arguments
        if (args.length != 1) {
            System.out.println("Not correct amount of args - <port number>");
            return;  // end program, not correct amount of args
        }

        //parse out the port number
        String[] arrInput = args[0].split("=");


        //opens a socket at the given port number - opens up a reader and writer
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(arrInput[1]));
        Socket clientSocket = serverSocket.accept();
        PrintWriter wOut = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader rIn = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));

        //reads the line in, then sends the line back
        String strInput;
        while ((strInput = rIn.readLine()) != null) {
            System.out.println(strInput);
         wOut.println(strInput);
        }
    }
}
