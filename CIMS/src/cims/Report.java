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
import java.util.List;
import java.util.stream.Collectors;
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
    private String locationGPS;
    private String locationName;

    private String weather;
    private String title;
    private transient ArrayList<Helpline> helpLines;
    private transient LocalDateTime startDate;
    private transient LocalDateTime endDate;
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
    public Report(int reportID, String description, String extraInformation, String location, String weather, ArrayList<Helpline> helpline, String title, String locationName) {
        this.reportID = reportID;
        this.description = description;
        this.extraInformation = extraInformation;
        this.locationGPS = location;
        this.weather = weather;
        this.helpLines = helpline;
        this.title = title;
        this.locationName = locationName;
        this.employees = FXCollections.observableArrayList();
    }

    public Report(int reportID, String description, String extraInformation, String location, String weather, ArrayList<Helpline> helpline, String title, String locationName, LocalDateTime startDate, LocalDateTime endDate) {
        this.reportID = reportID;
        this.description = description;
        this.extraInformation = extraInformation;
        this.locationGPS = location;
        this.weather = weather;
        this.helpLines = helpline;
        this.title = title;
        this.locationName = locationName;
        this.startDate = startDate;
        this.endDate = endDate;
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
     * gets the locationGPS
     *
     * @return the locationGPS of the report
     */
    public String getLocationGPS() {
        return locationGPS;
    }

    /**
     * sets the locationGPS of the report
     *
     * @param Location locationGPS to be set
     */
    public void setLocation(String Location) {
        this.locationGPS = Location;
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
     *
     * @return if it succeeded
     */
    public boolean saveReport() {
        boolean succes = false;
        dbm = new DatabaseManager();
        try {
            this.reportID = dbm.saveReport(this);
            if (this.reportID == 0) {
                return succes;
            }
            for (Helpline help : helpLines) {
                succes = dbm.saveHelplineReport(this.reportID, help.getID());
            }
        } catch (Exception e) {
            succes = false;
            System.out.println(e);
        }

        clientSocket cs = new clientSocket(this, 1);
        return succes;
    }

    /**
     * Gets the start date
     *
     * @return the startdate
     */
    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    /**
     * Gets the end date
     *
     * @return the enddate
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDateTime date) {
        this.endDate = date;
    }

    /**
     * Adds an employee to the report
     *
     * @param emp the employee to be added
     * @return Succeeded
     */
    public boolean addEmployee(Employee emp) {
        if (emp == null) {
            return false;
        }
        boolean succeeded = false;
        if (!employees.contains(emp)) {
            this.employees.add(emp);
            succeeded = true;
        }
        return succeeded;
    }

    public void addHelpline(Helpline h) {
        helpLines.add(h);
    }

    public void removeEmployee(Employee emp) {
        this.employees.remove(emp);
    }

    /**
     * Gets all assigned employees to this report
     *
     * @return The assigned employees
     */
    public ObservableList<Employee> getEmployees() {
        return this.employees;
    }

    /**
     * Sets the employee list
     *
     * @param emps list with employees
     */
    public void setEmployees(ObservableList<Employee> emps) {
        this.employees = emps;
    }

    /**
     * Returns the title
     *
     * @return title
     */
    @Override
    public String toString() {
        return title;
    }

    /**
     * gets the latitude
     *
     * @return latitude of a locationGPS
     */
    public double getLatitude() {
        String[] lat = locationGPS.split(",");
        return Double.parseDouble(lat[0].replaceAll("\\D+", ""));
    }

    /**
     * Get the longitude of a locationGPS
     *
     * @return
     */
    public double getLongitude() {
        String[] lng = locationGPS.split(",");
        return Double.parseDouble(lng[1].replaceAll("\\D+", ""));
    }

    /**
     * sets the latitude
     *
     * @param lat number to be set
     */
    public void setLatitude(double lat) {
        this.locationGPS = "[" + lat + "," + getLongitude() + "]";
    }

    /**
     * sets the longitude
     *
     * @param lng number to be set
     */
    public void setLongitude(double lng) {
        this.locationGPS = "[" + getLatitude() + "," + lng + "]";
    }
    
    public ObservableList<Employee> getEmployeesByHelpline(int id){
        List<Employee> emps = new ArrayList();
        for (Employee e : employees)
        {
            if(e.getHelpline() == null)
                continue;
            if (e.getHelpline().getID() == id)
            {
                emps.add(e);
            }
        }
        
        if(emps.isEmpty())
            return null;
        else
            return FXCollections.observableArrayList(emps);
    }
    
    public String getHelpLines() {
        return String.join(", ", helpLines.stream().map(Helpline::getName).collect(Collectors.toList()));
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void updateHelplines(int helplineID) {
        dbm = new DatabaseManager();
        dbm.saveHelplineReport(this.reportID, helplineID );
    }
    
    public ArrayList<Helpline> getHelplines()
    {
        return helpLines;
    }
}
