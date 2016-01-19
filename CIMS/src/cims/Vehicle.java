/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Database.DatabaseManager;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;

/**
 *
 * @author Cypher
 */
public class Vehicle {
    
    public int id;
    public String type;
    public int helplineId;
    public int regionId;
    public int inUse;
    public Employee assignedEmployee;
    private LocalDateTime start;
    private LocalDateTime end;
    
    public Vehicle(int id, String type, int helplineId, int inUse)
    {
        this.id = id;
        this.type = type;
        this.helplineId = helplineId;
        //this.regionId = regionId;
        this.inUse = inUse;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public String getType()
    {
        return this.type;
    }
    
    public int getHelplineId()
    {
        return this.helplineId;
    }
    
    public  int getRegionID()
    {
        return this.regionId;
    }
    
    public int getInUse()
    {
        return this.inUse;
    }
    
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Employee getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee employee) {
        this.assignedEmployee = employee;
    }
    
    public LocalDateTime getStart() {
        return start;
    }
    
    
    public LocalDateTime getEnd() {
        return end;
    }
}
