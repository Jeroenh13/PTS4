/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.util.Observable;

/**
 *
 * @author Bas
 */
class ChatObserver extends Observable {

    String text;

    public String getText() {
        System.out.println("Get text");
        return text;
    }

    public void setText(String text) {
        System.out.println("Set text");
        this.text = text;
        setChanged();
        notifyObservers();

        System.out.println("Ahit has been observed yo.");
    }

    public ChatObserver() {
        System.out.println("Created this thing");
    }
;

}
