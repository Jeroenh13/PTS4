/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseManager;
import Database.QueryBuilder;
import cims.Helpline;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Anna-May
 */
public class UnitsAssignControler {
    private Helpline helpline;
    private HashMap<String, ObservableList> specificationTypes;
    private HashMap<String, String> mySpecifications;
    private DatabaseManager dbm;
    
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
}
