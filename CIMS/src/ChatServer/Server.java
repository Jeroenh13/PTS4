/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import Server.StaticIPs;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
public class Server {
    
    /**
     * starts the server
     * @param args 
     */
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(StaticIPs.chatPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + StaticIPs.chatPort + ".");
            System.exit(1);
        }

        while (true) {
            try {
                Thread th = new Thread(new ChatServer(serverSocket.accept()));
                th.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
    }}

}
