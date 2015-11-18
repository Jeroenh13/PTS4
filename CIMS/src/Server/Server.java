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

    public static obvClass o;
    public static void main(String[] args) {
        o = new obvClass();
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
            serverSocket = new ServerSocket(9992);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 9990.");
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

    public static void sendingServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9991);
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
