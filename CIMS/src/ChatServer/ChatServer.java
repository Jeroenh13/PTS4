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
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
public class ChatServer implements Runnable, Observer {

    Socket client;
    ChatObserver chatObv;
    private InputStream inStream;
    private ObjectInputStream in;
    private OutputStream outStream;
    private ObjectOutputStream out;

    public ChatServer(Socket accept, ChatObserver obv) {
        this.chatObv = obv;
        this.client = accept;

    }

    @Override
    public void run() {

        System.out.println("Run");
        try {
            System.out.println("Added OBV");
            chatObv.addObserver(this);

            outStream = client.getOutputStream();
            inStream = client.getInputStream();
            in = new ObjectInputStream(inStream);
            out = new ObjectOutputStream(outStream);

            System.out.println("Waiting for text");
            while (true) {
                chatObv.setText((String) in.readObject());
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {

            System.out.println("Sending Text");
            out.writeObject(((ChatObserver) o).getText());
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
