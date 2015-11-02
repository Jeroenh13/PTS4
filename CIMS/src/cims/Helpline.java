/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.io.Serializable;
import Database.DatabaseManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;

/**
 *
 * @author kitty
 */
public class Helpline implements Serializable{
    
    private transient DatabaseManager dbm;
    
    private String name;
    private int ID;
    private ArrayList<Employee> employees;
    private Map<String, String> employeeMap;       

    
    /**
     * initializes an empty Helpline
     */
    public Helpline(){
    }
    
    /**
     * Creates a helpline with set values
     */
    public Helpline(int ID,String name)
    {
        this.ID = ID;
        this.name = name;
    }
        
    /**
     * Gets the ID
     *
     * @return ID of the helpline
     */
    public int getID() {
        return ID;
    }

    /**
     * sets the ID
     *
     * @param ID ID to be set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<Employee> getEmployees(String name) {
        return employees; 
    }
    
    public ArrayList<Employee> searchEmployees(HashMap employeeHashMap)
    {
        //HASHMAP!!!!
        //In db string = select * from view where key = value
        //toevoegen aan table
        //klooten in fxml required
        
        employeeMap = employeeHashMap;
        
        //employees = dbm.getUnits(hm);
        boolean first = true;
        String query = "SELECT * FROM vwEmployeeIncident WHERE ";
        //iterator
        for (Map.Entry<String, String> entry : employeeMap.entrySet())
        {
            if (first == true)
            {
                query += entry.getKey().toString() + " = " + entry.getValue().toString();
                first = false;
            }
            else
            {
                query += "AND " + entry.getKey().toString() + " = " + entry.getValue().toString();
            }
        }

        query += ";";
        
        //employees = dbm.getEmployees(query);
        
        return employees; 
    }
    
    // haal de waardes per specificatie met DISTINCT uit de vwemployees en vul de hashmap
    
    
    /**
     * Returns the helplines
     *
     * @return a list of helplines
     */
    public ArrayList<Helpline> getLines() {
        dbm = new DatabaseManager();
        return dbm.getHelpLines();
    }
}