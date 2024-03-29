/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.io.Serializable;
import Database.DatabaseManager;
import Database.QueryBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kitty
 */
public class Helpline implements Serializable{
    
    private transient DatabaseManager dbm;
    
    private String name;
    private int ID;
    private transient ObservableList<Employee> employees; 
    private transient ObservableList<Employee> employeesAss;
    private ObservableList<Report> reports;
    private ObservableList<Vehicle> vehicles;
    private ObservableList<Vehicle> assignedVehicles;
    
    /**
     * initializes an empty Helpline
     */
    public Helpline(){
    }
    
    /**
     * Creates a helpline with set values
     * @param ID sets the id
     * @param name sets the name
     */
    public Helpline(int ID,String name)
    {
        this.ID = ID;
        this.name = name;
        this.dbm = new DatabaseManager();
        //this.queryBuilder = new QueryBuilder();
        this.employees = FXCollections.observableArrayList();
        this.employeesAss = FXCollections.observableArrayList();
        this.reports = FXCollections.observableArrayList();
        this.vehicles = FXCollections.observableArrayList();
        this.assignedVehicles = FXCollections.observableArrayList();
    }
    
    public Helpline(int Id)
    {
        this.ID = Id;
        this.dbm = new DatabaseManager();
        //this.queryBuilder = new QueryBuilder();
        this.employees = FXCollections.observableArrayList();
        this.employeesAss = FXCollections.observableArrayList();
        this.reports = FXCollections.observableArrayList();
        this.vehicles = FXCollections.observableArrayList();
        this.name = dbm.getHelplineNameById(Id);
    }
        
    /**
     * Gets the ID
     *
     * @return ID of the helpline
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the ID
     *
     * @param ID ID to be set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setEmployees(ObservableList<Employee> employees) {
        this.employees = employees;
    }

    public void setEmployeesAss(ObservableList<Employee> employeesAss) {
        this.employeesAss = employeesAss;
    }

    public void setReports(ObservableList<Report> reports) {
        this.reports = reports;
    }
    
    /**
     * Gets the name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public ObservableList<Employee> getEmployees() {
        return employees; 
    }
    
    public ObservableList<Employee> getEmployeesAss() {
        return employeesAss; 
    }
    
    public ObservableList<Report> getReports(){
        return reports;
    }
    
    public ObservableList<Vehicle> getVehicles(){
        return vehicles;
    }
    
    /**
     * Method to receive employees given by the database-manager with selected specifications
     * 
     * @param specificationTypes
     * @param mySpecifications
     * @param ass
     * @param name
     * @param badgeNr
     * @param incident
     * @param fromDate
     * @param tillDate 
     */
    public void searchEmployees(HashMap<String, ObservableList> specificationTypes, HashMap<String, String> mySpecifications, boolean ass,String name, int badgeNr, String incident, LocalDate fromDate, LocalDate tillDate)
    {   
        String query = QueryBuilder.search(ass, mySpecifications, name, badgeNr, incident, fromDate, tillDate, this.name);
        if(ass == true){
            dbm.getEmployees(query, specificationTypes, employeesAss);
        }else{
            dbm.getEmployees(query, specificationTypes, employees);
        }
    }
    
    /**
     * Method to receive report given by the database-manager and bind employees to them
     */
    public void getIncidents(){
        reports.clear();
        String query = QueryBuilder.getNewIncidentsHelpline(this.name);
        dbm.getNewIncidents(query, reports);
        //query = QueryBuilder.getIncidentsHelpline(this.name);
        //dbm.getIncidents(query, reports);
        
        // bind employees to report
        for(Employee emp: employeesAss){
            if(emp.getAssignedTo() != null){
                for(Report report: reports){
                    if(report.getReportID() == emp.getAssignedTo().getReportID()){
                        report.addEmployee(emp); 
                    }
                }
            }
        }
    }
    
    /**
     * Returns the helplines
     * @return a list of helplines
     */
    public ArrayList<Helpline> getLines() {
        dbm = new DatabaseManager();
        return dbm.getHelpLines();
    }
    
    /**
     * Method to get an employee of a helpline with ID
     * 
     * @param id
     * @return an employee
     */
    public Employee getEmployeeWithID(int id){
        Employee emp = null;
        
        for(Employee emplo: this.employeesAss){
                if(emplo.getBadgeNR() == id){
                    emp = emplo;
                }
        }
        
        return emp;
    }
    
    public Employee getAllEmployeeWithID(int id){
        Employee emp = null;
        
        for(Employee emplo: this.employees){
                if(emplo.getBadgeNR() == id){
                    emp = emplo;
                }
        }
        
        return emp;
    }
    
    
    public Report getReportWithID(int id){
        Report rep = null;
        
        for(Report r : reports)
        {
            if(r.getReportID() == id)
                rep = r;
        }
        
        return rep;
    }
    
    public Vehicle getVehicleWithID(int id){
        Vehicle veh = null;
        
        for(Vehicle v : this.vehicles){
                if(v.getId()== id){
                    veh = v;
                }
        }
        
        return veh;
    }
    
    public void loadAllEmployees()
    {
        employees.clear();
        employees.addAll(dbm.getAllEmployees(name));
        //System.out.println("Number of employees found for " + name + ": " + employees.size());
        
        for(Employee e : employees){
            e.setHelpline(this);
        }
    }
    
    public void loadAllReports()
    {
        reports.clear();
        reports.addAll(dbm.getAllReports(name));
        //System.out.println("Number of reports found for " + name + ": " + reports.size());
    }
    
    public void addReport(Report rep)
    {
        reports.add(rep);
    }
    
    public void loadAllVehicles()
    {
        vehicles.clear();
        vehicles.addAll(dbm.getAllVehicles(ID));
        //System.out.println("Number of vehicles found for " + name + ": " + vehicles.size());
    }
    
    public void bindReportsToEmployees()
    {
        HashMap<Integer,Integer> assigned = dbm.getAssignedReports();
        int count = 0;
        
        
        for (Map.Entry<Integer, Integer> entry : assigned.entrySet())
        {
            Employee e = getAllEmployeeWithID(entry.getKey());
            Report r = getReportWithID(entry.getValue());
            
            if(e != null && r != null)
            {
                e.setAssignedTo(r);
                r.addEmployee(e);
                count++;
            }
        }
        //System.out.println("Number of assigned reports linked for " + name + ": " + count);
    }
    
    public void bindVehiclesToEmployees(List<PlannedVehicle> planning)
    {
        for(PlannedVehicle veh : planning)
        {
            Employee e = getAllEmployeeWithID(veh.getEmployeeId());
            Vehicle v = getVehicleWithID(veh.getVehicleId());
            
            if(e != null && v != null && v.getEnd() == null)
            {
                e.setAssignedVehicle(v);
                v.setAssignedEmployee(e);
                assignedVehicles.add(v);
            }
        }
    }
}
