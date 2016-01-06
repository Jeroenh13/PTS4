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
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
class acceptServerRc implements Runnable, Observer {

    private Socket socket;
    private obvClass obv;

    OutputStream outStream;
    InputStream inStream;
    ObjectInputStream in;
    ObjectOutputStream out;

    public acceptServerRc(Socket accept) {
        this.socket = accept;
    }

    @Override
    public void run() {
        try {

            outStream = socket.getOutputStream();
            inStream = socket.getInputStream();
            in = new ObjectInputStream(inStream);
            out = new ObjectOutputStream(outStream);
            
            //int id = (int) in.readObject();
            int id = 1;
            boolean found = false;
            System.out.println(obvClass.obvs);
            for (obvClass o : obvClass.obvs) {
                System.out.println(o.getId());
                if (o.getId() == id) {
                    this.obv = o;
                    found = true;
                    break;
                }
            }
            if (!found) {
                obv = new obvClass(id);
                obvClass.obvs.add(obv);
            }
            obv.addObserver(this);
            
            System.out.println("Added obv");
            while (true);

        } catch (IOException ex) {
            Logger.getLogger(acceptServerRc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            System.out.println("Sending repo");
            out.writeObject(((obvClass) o).returnReport());
        } catch (IOException ex) {
            Logger.getLogger(acceptServerRc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}