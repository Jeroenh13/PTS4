/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.HashSet;
import java.util.Observable;

/**
 *
 * @author Bas
 */
public class obvClass extends Observable{
    
    static HashSet<obvClass> obvs = new HashSet<obvClass>();
    
    private Object report;
    public final int ID;
    
    public obvClass (int ID)
    {
        this.ID = ID;
    }
    
    public void addNewReport(Object o)
    {
        report = o;
        setChanged();
        notifyObservers();
    }
    
    protected int getId()
    {
        System.out.println(ID);
        return ID;
    }

    public Object returnReport(){return report;}
}
