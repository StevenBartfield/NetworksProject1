/****************
 *Steven Bartfield - MPCS 54001 - Project 0
 ****************/

//code snips from http://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoClient.java

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientEcho {
    public static void main(String[] args) throws Exception {
        //tracks to make sure correct number of arguments
        if (args.length != 2) {
            System.out.println("Not correct amount of args - <host name> <port number>");
            return;  // end program, not correct amount of args
        }

        //parse input into code
        String strInputOne = args[0];
        String[] arrInputOne = strInputOne.split("=");
        String strInputTwo = args[1];
        String[] arrInputTwo = strInputTwo.split("=");

        //put command line args into variables
        String hostName;
        int portNumber;
        if (arrInputOne[0].equals("--serverIP")){
            hostName = arrInputOne[1];
            portNumber =  Integer.parseInt(arrInputTwo[1]);
        }
        else{
            hostName = arrInputTwo[1];
            portNumber =  Integer.parseInt(arrInputOne[1]);
        }

        //create socket, and writer in and out
        Socket sockEcho = new Socket(hostName, portNumber);
        PrintWriter wOut =  new PrintWriter(sockEcho.getOutputStream(), true);
        BufferedReader rIn = new BufferedReader(new InputStreamReader(sockEcho.getInputStream()));
        BufferedReader rStdIn = new BufferedReader(new InputStreamReader(System.in));

        String strInput;
        while ((strInput = rStdIn.readLine()) != null) {
            wOut.println(strInput);
        System.out.println("echo: " + rIn.readLine());
        }
    }
}
