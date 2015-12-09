/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.util.HashSet;
import java.util.Observable;

/**
 *
 * @author Bas
 */
class ChatObserver extends Observable {

    protected String text;
    protected int ID;
    
    
    protected static HashSet<ChatObserver> chats = new HashSet<ChatObserver>();

    protected String getText() {
        System.out.println("Get text");
        return text;
    }

    protected void setText(String text) {
        System.out.println("Set text");
        this.text = text;
        setChanged();
        notifyObservers();

    }

    protected  ChatObserver() {
    }
    
    protected ChatObserver(int Id){this.ID = Id;}

    protected int getId()
    {return ID;}
}
