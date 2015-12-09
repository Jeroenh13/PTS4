/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Bas
 */
public class Server {

    protected static obvClass o;
    
    /**
     * Starts the server
     * @param args 
     */
    public static void main(String[] args) {
        o = new obvClass();
        Runnable r = () -> {
            receivingServer();
        };
        Thread t = new Thread(r);
        t.start();
        sendingServer();
    }

    /**
     * creates a new server on port 9990 for incoming data
     */
    public static void receivingServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(StaticIPs.chatPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + StaticIPs.chatPort + ".");
            System.exit(1);
        }

        while (true) {
            try {
                // create new Thread which runs Multiserverrunnable
                Thread th = new Thread(new acceptServerRc(serverSocket.accept(),o));
                // start Thread
                th.start();
            } catch (IOException ex) {

            }
        }
    }

    /**
     * Creates a new server on port StaticIPs.serverPortSend for sending over data
     */
    public static void sendingServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(StaticIPs.serverPortSend);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 9991.");
            System.exit(1);
        }

        while (true) {
            try {
                // create new Thread which runs Multiserverrunnable
                Thread t = new Thread(new acceptServer(serverSocket.accept(),o));
                // start Thread
                t.start();
            } catch (IOException ex) {

            }
        }
    }
}
