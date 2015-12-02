/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Database.DatabaseManager;
import Server.clientSocket;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bas
 */
public class Report implements Serializable {

    private transient DatabaseManager dbm;
    private int reportID;
    private String description;
    private String extraInformation;
    private String location;
    private String weather;
    private String title;
    private ArrayList<Helpline> helpLines;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private transient ObservableList<Employee> employees;

    /**
     * *
     * creates a new empty report
     */
    public Report() {

    }

    /**
     * *
     * creates a new empty report
     *
     * @param reportID
     * @param description
     * @param title
     * @param startDate
     * @param endDate
     */
    public Report(int reportID, String description, String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.reportID = reportID;
        this.description = description;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employees = FXCollections.observableArrayList();
    }

    /**
     * creates a new filled report
     *
     * @param reportID
     * @param description
     * @param extraInformation
     * @param location
     * @param weather
     * @param helpline
     * @param title
     */
    public Report(int reportID, String description, String extraInformation, String location, String weather, ArrayList<Helpline> helpline, String title) {
        this.reportID = reportID;
        this.description = description;
        this.extraInformation = extraInformation;
        this.location = location;
        this.weather = weather;
        this.helpLines = helpline;
        this.title = title;
        this.employees = FXCollections.observableArrayList();
    }
    
    /**
     * gets the report ID
     *
     * @return the reportID
     */
    public int getReportID() {
        return reportID;
    }

    /**
     * sets the reportID
     *
     * @param reportID the report id to be set
     */
    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    /**
     * gets the description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description
     *
     * @param description the description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets the extra information
     *
     * @return the extra information
     */
    public String getExtraInformation() {
        return extraInformation;
    }

    /**
     * sets the extra information
     *
     * @param ExtraInformation the information to be set
     */
    public void setExtraInformation(String ExtraInformation) {
        this.extraInformation = ExtraInformation;
    }

    /**
     * gets the location
     *
     * @return the location of the report
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location of the report
     *
     * @param Location location to be set
     */
    public void setLocation(String Location) {
        this.location = Location;
    }

    /**
     * gets the weather
     *
     * @return the weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * sets the weather
     *
     * @param Weather weather to be set
     */
    public void setWeather(String Weather) {
        this.weather = Weather;
    }

    /**
     * gets the title
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title
     *
     * @param title title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Inserts the report into the database and sends it to the server.
     * @return if it succeeded
     */
    public boolean saveReport() {
        boolean succes = false;
        dbm = new DatabaseManager();
        try {
            for (Helpline help : helpLines) {
                succes = dbm.saveReport(this, help.getID());
            }
        } catch (Exception e) {
            succes = false;
            System.out.println(e);
        }

        clientSocket cs = new clientSocket(this);
        return succes;
    }

    /**
     * Gets the start date
     * @return the startdate
     */
    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    /**
     * Adds an employee to the report
     * @param emp the employee to be added
     * @return Succeeded
     */
    public boolean addEmployee(Employee emp) {
        if(emp == null)return false;
        boolean succeeded = false;
        if (!employees.contains(emp)) {
            this.employees.add(emp);
            succeeded = true;
        }
        return succeeded;
    }

    /**
     * Removes an employee from the report
     * @param emp employee to be removed
     */
    public void removeEmployee(Employee emp) {
        this.employees.remove(emp);
    }

    /**
     * Gets all assigned employees to this report 
     * @return The assigned employees
     */
    public ObservableList<Employee> getEmployees() {
        return this.employees;
    }

    /**
     * Sets the employee list
     * @param emps list with employees
     */
    public void setEmployees(ObservableList<Employee> emps) {
        this.employees = emps;
    }

    /**
     * Returns the title
     * @return title
     */
    @Override
    public String toString() {
        return title;
    }
}
