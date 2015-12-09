/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Database.DatabaseManager;
import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 * @author Anna-May
 */
public class Employee {

    private int badgeNR;
    private String name;
    private String available;
    private String function;
    private String department;
    private String region;
    private String commune;
    private String level;
    private String team;
    private Report assignedTo;
    private LocalDateTime start;
    private LocalDateTime end;
    private Helpline helpline;
    
    public Employee(int badgeNR, String name, String function, String available, String department, String region, String commune, String level, String team, 
            Report assignedTo, LocalDateTime start, LocalDateTime end){
        this.badgeNR = badgeNR;
        this.name = name;
        this.available = available;
        this.function = function;
        this.department = department;
        this.region = region;
        this.commune = commune;
        this.level = level;
        this.team = team;
        this.assignedTo = assignedTo;
        this.start = start;
        this.end = end;
    }
    
    public Employee(int badgeNr,String name, Helpline helpline)
    {
        this.badgeNR = badgeNr;
        this.name = name;
        this.helpline = helpline;
    }
    
    /**
     * @return the badgenummer as String
     */
    public int getBadgeNR() {
        return badgeNR; //String.valueOf(
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the available
     */
    public String getAvailable() {
        return available;
    }
    
    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }
    
    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }
    
    /**
     * @return the commune
     */
    public String getCommune() {
        return commune;
    }
    
    /**
     * @return the assigned report
     */
    public Report getAssignedTo() {
        return assignedTo; 
    }
    
    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }
    
    /**
     * @return the team
     */
    public String getTeam() {
        return team;
    }
    
    /**
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }
    
    /**
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }
    
    /**
     * @return the title of report for tableview
     */
    public String getTitle() {
        if(assignedTo != null){
            return assignedTo.getTitle();
        }else{
            return " ";
        }
    }

    /**
     * @param badgeNr the badgenr to set
     */
    public void setBadgeNr(int badgeNr) {
        this.badgeNR = badgeNr;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param available the available to set
     */
    public void setAvailable(String available) {
        this.available = available;
    }
    
    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }
    
    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }
    
    /**
     * @param commune the commune to set
     */
    public void setCommune(String commune) {
        this.commune = commune;
    }
    
    /**
     * @param assignedTo the assigned to report to set
     */
    public void setAssignedTo(Report assignedTo) {
        this.assignedTo = assignedTo; 
    }
    
    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }
    
    /**
     * @param team the team to set
     */
    public void setTeam(String team) {
        this.team = team;
    }
    
    /**
     * @param start the start to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    /**
     * @param end the end to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    
    @Override
    public String toString(){
        return this.name + " " + this.function;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    
    public static Employee getEmployeeByInlog(String userName, String password) {
        Database.DatabaseManager dm = new DatabaseManager();
        return dm.getEmployees(userName,password);
    }
}
