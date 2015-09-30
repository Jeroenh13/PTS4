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
    
    private DatabaseManager dbm;
    
    private String name;
    private ArrayList<Employee> employees;
    private Map<String, String> m;       

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
    
    public ArrayList<Employee> searchEmployees(HashMap hm)
    {
        //HASHMAP!!!!
        //In db string = select * from view where key = value
        //toevoegen aan table
        //klooten in fxml required
        
        m = hm;
        
        //employees = dbm.getUnits(hm);
        boolean first = true;
        String query = "SELECT * FROM vwPersoneelMeldingen WHERE "; //klopt de naam van de view?
        for (Map.Entry<String, String> entry : m.entrySet())
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
        
        employees = dbm.getUnits(query);
        
        return employees; 
    }
}
