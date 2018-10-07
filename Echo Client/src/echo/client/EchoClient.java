/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echo.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author Sam
 */
public class EchoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            Socket sock = new Socket("localhost", 6013); //Create socket with server
            String echoRequest = null;
            
            System.out.println("This application will send your data to the server, which will echo it back. Enter the character '~' to exit.");
            
            
            do {
                System.out.print("Phrase to server: "); //Get echo from user
                echoRequest = scan.nextLine();
                byte[] serverOutData = echoRequest.getBytes();

                sendMessage(serverOutData, sock); //Send echo request to server
                String echoedString = recieveEcho(sock); //Get response back

                System.out.println("Server reply> " + echoedString);
            } while (!echoRequest.equals("~"));
            
            
            sock.close(); //Close connection
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    public static void sendMessage(byte[] sendEcho, Socket sock) {
        try {
            DataOutputStream out = new DataOutputStream(sock.getOutputStream()); //Create byte stream to server
            out.write(sendEcho); //Write the byte to the server
            
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    public static String recieveEcho(Socket sock) {
        String outDataString = null;
        byte[] serverInData = new byte[8000];
        
        try {
            InputStream in = sock.getInputStream();
            ByteArrayOutputStream inBuffer = new ByteArrayOutputStream();
            
            if((in.read(serverInData, 0, serverInData.length)) != 1){
            inBuffer.write(serverInData);
            }
            
            inBuffer.flush();
            outDataString = new String(inBuffer.toByteArray());
            inBuffer.close();

        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        return outDataString;
    }

}
