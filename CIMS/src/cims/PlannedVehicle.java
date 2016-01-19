/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.time.LocalDateTime;

/**
 *
 * @author Jurgen
 */
public class PlannedVehicle {
    
    public int vehicleId;
    public int employeeId;
    public LocalDateTime start;
    public LocalDateTime end;
    
    public PlannedVehicle(int vehicleId, int employeeId, LocalDateTime start, LocalDateTime end)
    {
        this.vehicleId = vehicleId;
        this.employeeId = employeeId;
        this.start = start;
        this.end = end;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    
    
    public LocalDateTime getStart() {
        return start;
    }
    
    
    public LocalDateTime getEnd() {
        return end;
    }
}
