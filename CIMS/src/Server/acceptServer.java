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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
public class acceptServer implements Runnable {

    private Socket socket;
    private obvClass obv;
    OutputStream outStream;
    InputStream inStream;
    ObjectInputStream in;
    ObjectOutputStream out;

    public acceptServer(Socket accept,obvClass obv) {
        this.socket = accept;
        this.obv = obv;
    }

    @Override
    public void run() {
        try {
            outStream = socket.getOutputStream();
            inStream = socket.getInputStream();
            in = new ObjectInputStream(inStream);
            out = new ObjectOutputStream(outStream);
            obv.addNewReport(in.readObject());
        } catch (IOException ex) {
            Logger.getLogger(acceptServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(acceptServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
