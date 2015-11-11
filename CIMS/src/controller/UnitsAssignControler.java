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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
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
    
    public UnitsAssignControler(){
        this.specificationTypes = new LinkedHashMap<>();
        this.mySpecifications = new HashMap<>();
        this.dbm = new DatabaseManager();
        
        // for test
        helpline = new Helpline(1,"Politie");
    }
    
    public void logIn(String name, String password){
        // TO DO
        // get type unit
        // fill list with persons of unit 
        // wordt er hierbij ook gekeken of de persoon een functie heeft waarbij het die gegevens mag opvragen?
    }
    
    public HashMap<String, ObservableList> getSpecificationsTypes(){
        return specificationTypes;
    }
    
    public Helpline getHelpline(){
        return helpline;
    }
    
    public void fillSpecificationsTypes(){     
        dbm.getSpeciafications(specificationTypes);
        String query = QueryBuilder.getSpecificationValues(helpline.getName(), specificationTypes);
        dbm.getSpeciaficationsValues(query,specificationTypes);
    }
    
    public void adjustSpecification(String key, String value){
        // reset, add or adjust
        mySpecifications.put(key, value);
    }
    
    public void resetSpecifications(){
        mySpecifications.clear();
    }
    
    public void search(boolean ass,String name, int badgeNr, String incident, LocalDate fromDate, LocalDate tillDate){
        helpline.searchEmployees(specificationTypes, mySpecifications, ass, name, badgeNr, incident, fromDate, tillDate);
    }
    
    public void getIncidents(){
        helpline.getIncidents();
    }
    
    public void setSelectedEmployee(Employee emp){
        this.selectedEmployee = emp;
    }
    
    public void setSelectedReport(Report report){
        this.selectedReport = report;
        this.changedEmpsForReport = FXCollections.observableArrayList(selectedReport.getEmployees());
//        for(Employee emp :selectedReport.getEmployees()){
//            try {
//                changedEmpsForReport.add((Employee) emp.clone());
//            } catch (CloneNotSupportedException ex) {
//                Logger.getLogger(UnitsAssignControler.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        //selectedReport.getEmployees()
    }
    
    public Employee getSelectedEmployee(){
        return this.selectedEmployee;
    }
    
    public Report getSelectedReport(){
        return this.selectedReport;
    }
    
    public boolean addEmployee(){
        boolean succeded = false;
        if(!changedEmpsForReport.contains(this.selectedEmployee)){ 
            this.changedEmpsForReport.add(this.selectedEmployee);
            succeded = true;
        }
        return succeded;
    }
    
    public void removeEmployee(){
        this.changedEmpsForReport.remove(this.selectedEmployee);
    }
    
    public boolean adjustDate(LocalDateTime start, LocalDateTime end){
        boolean succeded = false;
        selectedEmployee.setStart(start);
        selectedEmployee.setEnd(end);
        return succeded;
    }
    
    public void saveEmpForReport(){
        this.selectedReport.setEmployees(FXCollections.observableList(this.changedEmpsForReport));
    }
    
    public ObservableList getEmployeesForReport(){
        return this.changedEmpsForReport;
    }
}
