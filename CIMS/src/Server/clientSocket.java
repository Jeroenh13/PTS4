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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
public class clientSocket {

    private Socket client;
    private InputStream inStream;
    private ObjectInputStream in;
    private OutputStream outStream;
    private ObjectOutputStream out;
    private int port = 9991;

    public clientSocket(Report r) {
        try {
            client = new Socket("192.168.178.11", port);
            outStream = client.getOutputStream();
            out = new ObjectOutputStream(outStream);
            inStream = client.getInputStream();
            in = new ObjectInputStream(inStream);
            out.writeObject(r);
        } catch (IOException ex) {
            Logger.getLogger(clientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
