/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Database.DatabaseManager;
import java.util.ArrayList;

/**
 *
 * @author Bas
 */
public class Report {

    private DatabaseManager dbm;
    private int reportID;
    private String description;
    private String ExtraInformation;
    private String Location;
    private String Weather;
    private String title;
    private ArrayList<Helpline> helpLines;

    /**
     * *
     * creates a new empty report
     */
    public Report() {

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
     */
    public Report(int reportID, String description, String extraInformation, String location, String weather, ArrayList<Helpline> helpline,String title) {
        this.reportID = reportID;
        this.description = description;
        this.ExtraInformation = extraInformation;
        this.Location = location;
        this.Weather = weather;
        this.helpLines = helpline;
        this.title = title;
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
        return ExtraInformation;
    }

    /**
     * sets the extra information
     *
     * @param ExtraInformation the information to be set
     */
    public void setExtraInformation(String ExtraInformation) {
        this.ExtraInformation = ExtraInformation;
    }

    /**
     * gets the location
     *
     * @return the location of the report
     */
    public String getLocation() {
        return Location;
    }

    /**
     * sets the location of the report
     *
     * @param Location location to be set
     */
    public void setLocation(String Location) {
        this.Location = Location;
    }

    /**
     * gets the weather
     *
     * @return the weather
     */
    public String getWeather() {
        return Weather;
    }

    /**
     * sets the weather
     *
     * @param Weather weather to be set
     */
    public void setWeather(String Weather) {
        this.Weather = Weather;
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
    
    
    public boolean saveReport()
    {
        boolean succes = false;
        dbm = new DatabaseManager();
        try{
            for(Helpline help : helpLines)
            {
                succes = dbm.saveReport(this,help.getID());
            }
        }
        catch(Exception e)
        {
            succes = false;
            System.out.println(e);
        }
        return succes;
    }
}
