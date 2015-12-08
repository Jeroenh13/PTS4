/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseManager;
import Database.QueryBuilder;
import cims.Employee;
import cims.Helpline;
import cims.Report;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class, controler is a hatch to get the content for de gui. The content is being put into place by the UnitAssignFXControler.
 * The UnitAssignFXControler extends this class.
 * 
 * @author Anna-May
 */
public class UnitsAssignControler {
    private Helpline helpline;
    private final HashMap<String, ObservableList> specificationTypes;
    private final HashMap<String, String> mySpecifications;
    private final DatabaseManager dbm;
    private Report selectedReport;
    private Employee selectedEmployee;
    private ObservableList<Employee> changedEmpsForReport;
    
    /**
     * Contructor of this controler. 
     * Creates a UnitsAssignControler() with values
     */
    public UnitsAssignControler(){
        this.specificationTypes = new LinkedHashMap<>();
        this.mySpecifications = new HashMap<>();
        this.dbm = new DatabaseManager();
        
        // for test
        helpline = new Helpline(1,"Politie");
    }
    
    /**
     * This method is meant for the login of the person of a helpline That is 
     * going to divide the employees overall the incidents.
     * 
     * @param name
     * @param password 
     */
    public void logIn(String name, String password){
        // TO DO
        // get type unit
        // fill list with persons of unit 
        // wordt er hierbij ook gekeken of de persoon een functie heeft waarbij het die gegevens mag opvragen?
    }
    
    /**
     * get the specifications types
     * 
     * @return SpecificationsTypes for the search function
     */
    public HashMap<String, ObservableList> getSpecificationsTypes(){
        return specificationTypes;
    }
    
    /**
     * Gets the helpline
     * 
     * @return helpline of the user
     */
    public Helpline getHelpline(){
        return helpline;
    }
    
    /**
     * This method fills the types of specifications with values for the search function.
     */
    public void fillSpecificationsTypes(){     
        dbm.getSpeciafications(specificationTypes);
        String query = QueryBuilder.getSpecificationValues(helpline.getName(), specificationTypes);
        dbm.getSpeciaficationsValues(query,specificationTypes);
    }
    
    /**
     * When the values of the specifications are adjusted this method is called to adjust the search values.
     * Reset, add or adjust.
     * 
     * @param key
     * @param value 
     */
    public void adjustSpecification(String key, String value){
        mySpecifications.put(key, value);
    }
    
    /**
     * This method is meant to reset the specifications.
     */
    public void resetSpecifications(){
        mySpecifications.clear();
    }
    
    /**
     * Every helpline has got employees. This method provides and refreshes them for the user interface filtered by the search values.
     * 
     * @param ass
     * @param name
     * @param badgeNr
     * @param incident
     * @param fromDate
     * @param tillDate 
     */
    public void search(boolean ass,String name, int badgeNr, String incident, LocalDate fromDate, LocalDate tillDate){
        helpline.searchEmployees(specificationTypes, mySpecifications, ass, name, badgeNr, incident, fromDate, tillDate);
    }
    
    /**
     * Get the unsolved incidents.
     */
    public void getIncidents(){
        helpline.getIncidents();
    }
    
    /**
     * Set the selected employee to use at other methods.
     * 
     * @param emp 
     */
    public void setSelectedEmployee(Employee emp){
        this.selectedEmployee = emp;
    }
    
    /**
     * Set the selected report to use at other methods.
     * 
     * @param report 
     */
    public void setSelectedReport(Report report){
        this.selectedReport = report;
        if(selectedReport != null){
            this.changedEmpsForReport = FXCollections.observableArrayList(selectedReport.getEmployees());
        }
    }
    
    /**
     * Gets the selected employee
     * 
     * @return selected employee
     */
    public Employee getSelectedEmployee(){
        return this.selectedEmployee;
    }
    
    /**
     * Gets the selected report
     * 
     * @return selected report
     */
    public Report getSelectedReport(){
        return this.selectedReport;
    }
    
    /**
     * Added an employee to an unsaved list meant for the selected report.
     * 
     * @return a boolean to know if it succeeded
     */
    public boolean addEmployee(){
        boolean succeded = false;
        if(!changedEmpsForReport.contains(this.selectedEmployee)){ 
            this.changedEmpsForReport.add(this.selectedEmployee);
            succeded = true;
        }
        return succeded;
    }
    
    /**
     * Removes a person from the unsaved list meant for the selected report.
     */
    public void removeEmployee(){
        this.changedEmpsForReport.remove(this.selectedEmployee);
    }
    
    /**
     * Adjusts the start date time and end date time for the unsaved list meant for the selected report.
     * 
     * @param start
     * @param end
     * @param emp
     * @return 
     */
    public boolean adjustDate(LocalDateTime start, LocalDateTime end, Employee emp){
        boolean succeded = false;
        emp.setStart(start);
        emp.setEnd(end);
        return succeded;
    }
    
    /**
     * With this method a string presenting a message concluding if saving employees for report has succeeded is returned.
     * This method saves and adjust the employees that have been added to the selected report. 
     * 
     * @return a string presenting a message concluding if saving employees for report has succeeded
     */
    public String saveEmpForReport(){
        String message = "";
        for(Employee emp : changedEmpsForReport){
            if(emp.getStart() == null){
                message += "Startdate of " + emp.getName() + " is not defined \n";
            }
        }
        
        if(message.equals("")){ 
            for(Employee emp : changedEmpsForReport){
                boolean found = false;
                if(selectedReport != null){
                    for(Employee empR: selectedReport.getEmployees()){
                        if(empR.getBadgeNR() == emp.getBadgeNR()){ 
                            dbm.updateEmployeeForReport(emp, selectedReport, this.helpline.getName());
                            found = true;
                        }
                    }
                }
                
                
                if(found == false){
                    //dbm.insertEmployeeForReport(emp, selectedReport, this.helpline.getName());
                    dbm.saveEmployeeForReport(emp, selectedReport, this.helpline.getName());
                }

                found = false;
            }
            
            selectedReport.setEmployees(FXCollections.observableList(this.changedEmpsForReport));
            changedEmpsForReport = FXCollections.observableArrayList(selectedReport.getEmployees());
        }
        
        return message;
    }
    
    /**
     * Returns the unsaved list of employees for the selected report
     * 
     * @return 
     */
    public ObservableList<Employee> getEmployeesForReport(){
        return changedEmpsForReport;
    }
    
    /**
     * Gives a employee given by the id out of the helpline.
     * 
     * @param id
     * @return a employee
     */
    public Employee getEnployeeWithID(int id){
        return helpline.getEmployeeWithID(id);
    }
    
    /**
     * Gives a employee given by the id out of the unsaved employees for report.
     * 
     * @param id
     * @return a employee
     */
    public Employee getEmployeeWithIDReport(int id){
        Employee emp = null; 
        for(Employee emplo :  this.changedEmpsForReport){
            if(id == emplo.getBadgeNR()){
                emp = emplo;
            }
        }
        
        return emp;
    }      
}
