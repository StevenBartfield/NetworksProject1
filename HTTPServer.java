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
import java.io.File;
import java.util.ArrayList;

public class HTTPServer {

    public static ArrayList<String> fileList = new ArrayList<String>();

    public static ArrayList<String> getFileStructure(String root)
    {
	File rootLocation = new File(root);
	File rootContents[] = rootLocation.listFiles();
	int j = 0;
        for (int i = 0; i<rootContents.length; i++)
        {
		if (rootContents[i].isDirectory())
		{
			getFileStructure(rootContents[i].getPath());//recursively find files
		}

		else
		{
			fileList.add(j, rootContents[i].getAbsolutePath());
			j++;
		}   
        }
	return fileList;
  
    }
	
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

	//get file structure
	ArrayList<String> files = getFileStructure("www/");
	//print below to see what it looks like
/*	for (int i=0; i<files.size(); i++)
	{
		System.out.println(files.get(i));
	}
*

        //opens a socket at the given port number - opens up a reader and writer
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(arrInput[1]));
        Socket clientSocket = serverSocket.accept();
        PrintWriter wOut = new PrintWriter(clientSocket.getOutputStream(), true);
	BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

/*
	System.out.println("Here is the client request:");
	String inputLine;
        while ((inputLine = in.readLine()) != null)	{
        	System.out.println(inputLine);
	}
*/
	out.write("HTTP/1.0 200 OK\r\n");
	out.write("Date: "+datetime+"\r\n");
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
