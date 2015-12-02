/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import cims.Report;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
public class ClientReceiving extends Observable implements Runnable {

    private Socket client;
    private InputStream inStream;
    private ObjectInputStream in;
    private OutputStream outStream;
    private ObjectOutputStream out;
    private final int port = 9992;

    /**
     * Creates a new receiving Client
     */
    public ClientReceiving() {

    }

    /**
     * Receives stuffs
     */
    @Override
    public void run() {
        try {
            // TODO code application logic here
            client = new Socket("192.168.178.11", port);
            outStream = client.getOutputStream();
            out = new ObjectOutputStream(outStream);
            inStream = client.getInputStream();
            in = new ObjectInputStream(inStream);
            while (true) {
                Report r = (Report) in.readObject();
                setChanged();
                notifyObservers();
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientReceiving.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
