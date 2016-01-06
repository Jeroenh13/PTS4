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
class acceptServer implements Runnable {

    private Socket socket;
    private obvClass obv;
    OutputStream outStream;
    InputStream inStream;
    ObjectInputStream in;
    ObjectOutputStream out;

    public acceptServer(Socket accept) {
        this.socket = accept;

        System.out.println("Accepted");
    }

    @Override
    public void run() {
        try {
            outStream = socket.getOutputStream();
            inStream = socket.getInputStream();
            in = new ObjectInputStream(inStream);
            out = new ObjectOutputStream(outStream);

            System.out.println("Created out and ins");
            int id = in.readInt();
            System.out.println("Read int");
            System.out.println(id);
            boolean found = false;
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

            while (true) {
                System.out.println("waiting for repo");
                obv.addNewReport(in.readObject());
                System.out.println("Received repo");
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(acceptServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
