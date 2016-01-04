/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseManager;
import cims.Helpline;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kitty
 */
public class CentralController 
{
    private final DatabaseManager dbm;
    private List<Helpline> helplines = new ArrayList<>();
    
    
    public CentralController()
    {
        this.dbm = new DatabaseManager();
        loadHelplines();
    }
    
    public void savePlan(String approach, String helpline, int reportID)
    {
        int helplineID = 0;
        for (Helpline h : helplines) {
            if (h.getName().equals(helpline))
            {
                helplineID = h.getID();
            }
        }
        dbm.saveApproach(approach, helplineID, reportID);
    }
    
    private void loadHelplines()
    {
        helplines = dbm.getHelpLines();
        
        for(Helpline h : helplines){
            h.loadAllEmployees();
            h.loadAllReports();
            h.loadAllVehicles();
            
            h.bindReportsToEmployees();
        }
    }
}
