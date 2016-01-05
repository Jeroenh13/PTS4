/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Observable;

/**
 *
 * @author Bas
 */
public class obvClass extends Observable{
    
    private Object report;
    
    
    public obvClass ()
    {
    
    }
    
    public void addNewReport(Object o)
    {
        report = o;
        setChanged();
        notifyObservers();
    }

    public Object returnReport(){return report;}
}
