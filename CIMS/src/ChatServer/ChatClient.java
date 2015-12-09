/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import Server.StaticIPs;

/**
 *
 * @author Bas
 */
public class ChatClient extends Observable implements Runnable {

    private Socket client;
    private ChatObserver chatObv;
    private InputStream inStream;
    private ObjectInputStream in;
    private OutputStream outStream;
    private ObjectOutputStream out;
    
    @Override
    public void run() {
          try {
            // TODO code application logic here
            
            
            client = new Socket(StaticIPs.chatIP,StaticIPs.chatPort);
            outStream = client.getOutputStream();
            out = new ObjectOutputStream(outStream);
            inStream = client.getInputStream();
            in = new ObjectInputStream(inStream);
            
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
    }   catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }}
    
    
    public void setText(String text)
    {
        try {
            out.writeObject(text);
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
