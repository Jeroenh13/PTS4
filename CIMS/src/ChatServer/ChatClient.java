/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

//test
/**
 *
 * @author Bas
 */
public class ChatClient {

    private static Socket client;
    private static InputStream inStream;
    private static ObjectInputStream in;
    private static OutputStream outStream;
    private static ObjectOutputStream out;
    private final static int port = 9993;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            // TODO code application logic here
            
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            client = new Socket("145.93.100.194", port);
            outStream = client.getOutputStream();
            out = new ObjectOutputStream(outStream);
            inStream = client.getInputStream();
            in = new ObjectInputStream(inStream);
            
            System.out.println("Enter chat room.");
            out.writeObject(Integer.valueOf(br.readLine()));
            Thread t =  new Thread(new Runnable() {

                @Override
                public void run() {
                    while(true)
                    {
                        try {
                            System.out.println("Waiting for text");
                            String input = (String)in.readObject();
                            System.out.println(input);
                        } catch (IOException ex) {
                            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            t.start();
            while (true) {
                System.out.println("Waiting for input");
                String read = br.readLine();
                if(!read.isEmpty())
                    out.writeObject(read);
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
