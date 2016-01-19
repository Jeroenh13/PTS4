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
    private String text;
    private final int id;

    /**
     * Creates a new client 
     * @param id id of the chat room
     */
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

    /**
     * Reads the send text and notifies the observers
     * @throws IOException Connection with the server
     * @throws ClassNotFoundException 
     */
    public synchronized void readText() throws IOException, ClassNotFoundException {
        Coder coder = new Coder();
        text = (String) in.readObject();
        text = coder.decrypt(text);
        setChanged();
        this.notifyObservers();
    }

    /**
     * Writes the text
     * @param text 
     */
    public void setText(String text) {
        try {
            Coder coder = new Coder();
            text = coder.encrypt(text);
            out.writeObject(text);
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the written text
     * @return text 
     */
    public String getText() {
        return text;
    }
}
