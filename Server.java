/****************
 *Steven Bartfield and Rehan Balagamwala- MPCS 54001 - Project 1
 ****************/

//code snips from http://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoServer.java

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.text.DateFormat;
import java.util.ArrayList;

public class Server {

    public static ArrayList<String> fileList = new ArrayList<String>();
    public static ArrayList<String> getFileStructure(String root)
    {
        File rootLocation = new File(root);
        File rootContents[] = rootLocation.listFiles();
        int j = 0;
        for (int i = 0; i<rootContents.length; i++) {
            if (rootContents[i].isDirectory()){
                getFileStructure(rootContents[i].getPath());//recursively find files
            }
            else{
                fileList.add(j, rootContents[i].getAbsolutePath());
                j++;
            }
        }
        return fileList;
    }

    //from http://stackoverflow.com/questions/16027229/reading-from-a-text-file-and-storing-in-a-string
    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public static void main(String[] args) throws Exception {
        //parse out the port number
        String[] arrInput = args[0].split("=");

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(arrInput[1]));
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        //DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());



        String strInput;


        strInput = in.readLine();
        System.out.println(strInput);
        File fileInput = new File(strInput);

        //HARDCODED FOR NOW
        String strHeader =  "HTTP/1.1 200 OK \r\n";
        strHeader += "Content-Length: ";
        strHeader += fileInput.length() + "\r\n";
        strHeader += "Content-Type: ";
        strHeader += "text/html" + "\r\n";

        //HEADER SENT BACK TO USER
        //out.writeBytes(strHeader + "\r\n\n");
        out.println(strHeader + "\r\n\n");


        String strOutput;
        int nPathEnd = strInput.indexOf("HTTP/") - 1;
        String strPath = strInput.substring(5, nPathEnd);
        File fileRequested = new File(strPath);

        System.out.println(strPath);
        //see if the file is infact there
        if (fileRequested.isFile()){
            System.out.println("The file exists!!");
        }

        strOutput = readFile(strPath);

        System.out.println(strOutput);//print to server

        //this is the line of code that is not sending to the client for some reason (or curl is not printing)!!!!!!!
        out.println(strOutput);  //print to client


        System.err.println("Connection terminated");
        out.close();
        in.close();
        clientSocket.close();




    }
}


//    public static void generateReponse(String filePath, PrintWriter out){
//        try{
//            //* error is at requestFile trying to write the response back to the server before we close the connection
//            File responseFile = new File(requestPath);
//            contentLength = responseFile.length();
//            System.out.println(contentLength);
//
//        }
//        catch(Exception e){
//        }
//
//        java.util.Date date1 = new java.util.Date();
//        out.println(protocol + " " + statusCode + " OK\n"
//                +date1 + "\n"
//                + "Accept-Ranges: bytes\n"
//                + "Content-Length: "+ contentLength+"\n"
//                + "Connection: "+ connectionStatus + "\n"
//                + "Content-Type: "+ extension+"\r\n");
//
//        try{
//            //* error is at requestFile trying to write the response back to the server before we close the connection
//            File responseFile = new File(requestPath);
//            Scanner responseScan = new Scanner(responseFile);
//
//            while(responseScan.hasNextLine()){
//                String response = responseScan.nextLine();
//                out.println(response);
//            }
//
//        }
//        catch(Exception e){
//        }
//    }