/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseManager;
import cims.Helpline;
import cims.Report;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public List<Helpline> getHelplines()
    {
        return helplines;
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
    
    public List<Field> getCollumsReport()
    {
        List fieldsList = new ArrayList<>();
        Class r = Report.class;
        Field[] fields = r.getDeclaredFields();
        //System.out.println(fields.toString());
        //System.out.println(fields.length);
        for (Field f : fields) {
            //System.out.println(f.getName());
            fieldsList.add(f);
        }
        return fieldsList;
    }
    
    private void fillIncidents()
    {
        
    }
}
