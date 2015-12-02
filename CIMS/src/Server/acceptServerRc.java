/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

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
public class acceptServerRc implements Runnable, Observer {

    private final Socket socket;
    private final obvClass obv;

    OutputStream outStream;
    InputStream inStream;
    ObjectInputStream in;
    ObjectOutputStream out;

    /**
     * Accepts a new socket on this port
     * @param accept socket where there is an connection from
     * @param obv class where there will be data from
     */
    public acceptServerRc(Socket accept, obvClass obv) {
        this.socket = accept;
        this.obv = obv;
    }

    /**
     * Initializes and adds the observable and keeps the process alive.
     */
    @Override
    public void run() {
        try {
            obv.addObserver(this);
            
            outStream = socket.getOutputStream();
            inStream = socket.getInputStream();
            in = new ObjectInputStream(inStream);
            out = new ObjectOutputStream(outStream);
            while(true);
        } catch (IOException ex) {
            Logger.getLogger(acceptServerRc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sends a report when updated.
     * @param o 
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            out.writeObject(((obvClass)o).returnReport());
        } catch (IOException ex) {
            Logger.getLogger(acceptServerRc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
