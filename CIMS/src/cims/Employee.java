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
    private Vehicle assignedVehicle;
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
     * Sets commune
     * 
     * @param commune the commune to set
     */
    public void setCommune(String commune) {
        this.commune = commune;
    }
    
    /**
     * Sets report that employee is assigned to
     * 
     * @param assignedTo the assigned to report to set
     */
    public void setAssignedTo(Report assignedTo) {
        this.assignedTo = assignedTo; 
    }
    
    /**
     * Sets level
     * 
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }
    
    /**
     * Sets team 
     * 
     * @param team the team to set
     */
    public void setTeam(String team) {
        this.team = team;
    }
    
    /**
     * Sets the start time and date for an employee that is set on a report
     * 
     * @param start the start to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    /**
     * Sets the end time and date for an employee that is set on a report
     * 
     * @param end the end to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    
    public Helpline getHelpline(){
        return this.helpline;
    }
    
    public void setHelpline(Helpline h){
        this.helpline = h;
    }
    
    /**
     * Method to return a string of employee (name and function) 
     * Example of use is the display (listbox) of employees being set for a report at unit assign
     * @return string with name and function of employee
     */
    @Override
    public String toString(){
        return this.name + " " + this.function;
    }
    
    /**
     * Method to get a clone of this object (Employee)
     * @return a clone of this object (Employee)
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
    
    public static Employee getEmployeeByInlog(String userName, String password) {
        Database.DatabaseManager dbm = new DatabaseManager();
        return dbm.getEmployees(userName,password);
    }
    
    @Override
    public boolean equals(Object object)
    {
        boolean same = true;

        if (object != null && object instanceof Employee)
        {
            Employee empOther = (Employee) object;
            if(this.badgeNR != empOther.getBadgeNR()){
                same = false;
            }
            
            if(!this.name.equals(empOther.getName())){
                same = false;
            }
            
            if(!this.available.equals(empOther.getAvailable())){
                same = false;
            }
            
            if(!this.function.equals(empOther.getFunction())){ 
                same = false;
            }
            
            if(!this.department.equals(empOther.getDepartment())){
                same = false;
            }
            
            if(!this.region.equals(empOther.getRegion())){
                same = false;
            }
            
            if(!this.commune.equals(empOther.getCommune())){
                same = false;
            }
            
            if(!this.level.equals(empOther.getLevel())){
                same = false;
            }
            
            if(!this.team.equals(empOther.getTeam())){ 
                same = false;
            }

//            if(this.assignedTo.){
//                
//            }
            if(this.start != null && empOther.getStart() != null){
                if(!this.start.equals(empOther.getStart())){
                    same = false;
                }
            }else if((null == this.start && empOther.getStart() != null) || (null != this.start && empOther.getStart() == null) ){
                same = false;
            }
            
            if(this.end != null && empOther.getEnd() != null){
                if(!this.end.equals(empOther.getEnd())){
                    same = false;
                }
            }else if((null == this.end && empOther.getEnd() != null) || (null != this.end && empOther.getEnd() == null) ){
                same = false;
            }
            
//            if(this.helpline.equals(empOther.geth)){
//                
//            }

        }else{
            same = false;
        }

        return same;
    }
}
