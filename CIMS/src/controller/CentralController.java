/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseManager;
import cims.Helpline;
import java.util.ArrayList;

/**
 *
 * @author kitty
 */
public class CentralController 
{
    private final DatabaseManager dbm;
    private int helplineID;
    
    public CentralController()
    {
        this.dbm = new DatabaseManager();
    }
    
    public void savePlan(String approach, String helpline)
    {
        ArrayList<Helpline> helplines = dbm.getHelpLines();
        for (Helpline h : helplines) {
            if (h.getName().equals(helpline))
            {
                helplineID = h.getID();
            }
        }
        dbm.saveApproach(approach, helplineID, 0);
    }
}
