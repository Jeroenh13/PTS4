/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
public class Server {
    public static void main(String[] args) {
        ChatObserver obv = new ChatObserver();
         ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9993);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 9993.");
            System.exit(1);
        }

        while (true) {
            try {
                Thread th = new Thread(new ChatServer(serverSocket.accept(),obv));
                th.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
    }}

}
