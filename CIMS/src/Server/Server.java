/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;

/**
 *
 * @author Bas
 */
public class Server {

    /**
     *
     */
    protected static obvClass o;

    /**
     * Starts the server
     *
     * @param args
     */
    public static void main(String[] args) {
        HashSet<obvClass> obvs = new HashSet();

        Runnable r = new Runnable() {

            @Override
            public void run() {
                receivingServer();
            }
        };
        Thread t = new Thread(r);
        t.start();
        sendingServer();
    }

    public static void receivingServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(StaticIPs.serverPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 9992.");
            System.exit(1);
        }

        while (true) {
            try {

                System.out.println("Looking for client");
                // create new Thread which runs Multiserverrunnable
                Thread th = new Thread(new acceptServerRc(serverSocket.accept()));

                System.out.println("Client found");
                // start Thread
                th.start();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }
    }

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
                Thread t = new Thread(new acceptServer(serverSocket.accept()));

                // start Thread
                t.start();
            } catch (IOException ex) {

            }
        }
    }
}
