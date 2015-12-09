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
import controller.OnTheRoadFXController;

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
    private String text;
    private int id;

    public ChatClient(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            client = new Socket(StaticIPs.chatIP, StaticIPs.chatPort);
            outStream = client.getOutputStream();
            out = new ObjectOutputStream(outStream);
            inStream = client.getInputStream();
            in = new ObjectInputStream(inStream);
            out.writeObject(id);

            while (true) {
                readText();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void readText() throws IOException, ClassNotFoundException {
        text = (String) in.readObject();
        setChanged();
        this.notifyObservers();
    }

    public void setText(String text) {
        try {
            out.writeObject(text);
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getText() {
        return text;
    }
}
