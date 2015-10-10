/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.util.Map;

/**
 *
 * @author Anna-May
 */
public class Employee {
    private String name;
    private String function;
    private String available;
    private String department;
    private String town;
    private int level;
    private String team;
    private Report appointedTo;
    
    public Employee(String name, String function, String available, String department, String town, int level, String team, Report appointedTo){
        this.name = name;
        this.function = function;
        this.available = available;
        this.department = department;
        this.town = town;
        this.level = level;
        this.team = team;
        this.appointedTo = appointedTo;
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

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return the available
     */
    public String getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the town
     */
    public String getTown() {
        return town;
    }

    /**
     * @param town the town to set
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the team
     */
    public String getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * @return the appointedTo
     */
    public Report getAppointedTo() {
        return appointedTo;
    }

    /**
     * @param appointedTo the appointedTo to set
     */
    public void setAppointedTo(Report appointedTo) {
        this.appointedTo = appointedTo;
    }    
}
