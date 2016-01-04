/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

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
public class ChatServer implements Runnable, Observer {

    private Socket client;
    private ChatObserver chatObv;
    private InputStream inStream;
    private ObjectInputStream in;
    private OutputStream outStream;
    private ObjectOutputStream out;

    /**
     * Starts a new server point to communicate with the client.
     * @param accept 
     */
    public ChatServer(Socket accept) {
        
        this.client = accept;

    }

    @Override
    public void run() {

        try {
            outStream = client.getOutputStream();
            inStream = client.getInputStream();
            in = new ObjectInputStream(inStream);
            out = new ObjectOutputStream(outStream);
            
            int id = (int)in.readObject();
            boolean found = false;
            for(ChatObserver chat: ChatObserver.chats)
            {
                if(chat.getID() == id)
                {
                    chatObv = chat;
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                chatObv = new ChatObserver(id);
                ChatObserver.chats.add(chatObv);
            }
            chatObv.addObserver(this);

            System.out.println("Waiting for text");
            while (true) {
                chatObv.setText((String) in.readObject());
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            out.writeObject(((ChatObserver) o).getText());
            System.out.println(((ChatObserver) o).getText());
        } catch (IOException ex) {
            chatObv.deleteObserver(this);
            System.out.println("Observer disconnected.");
        }
    }

}
