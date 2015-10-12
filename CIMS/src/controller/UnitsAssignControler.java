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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javafx.collections.FXCollections;
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
    private QueryBuilder queryBuilder;
    
    public UnitsAssignControler(){
        this.specificationTypes = new HashMap<>();
        this.mySpecifications = new HashMap<>();
        this.dbm = new DatabaseManager();
        this.queryBuilder = new QueryBuilder();
        
        // for test
        helpline = new Helpline(1,"Politie");
    }
    
    public void logIn(String name, String password){
        // TO DO
        // get type unit
        // fill list with persons of unit 
        // wordt er hierbij ook gekeken of de persoon een functie heeft waarbij het die gegevens mag opvragen?
    }
    
    public ArrayList<Employee> getListOfPersons(){
        // maak een query met de gegevens in de hashmap
        // bevraag het database voor een lijst met de gevraagde personen
        // return deze lijst voor de desbetreffende table
        return helpline.searchEmployees(mySpecifications);
    }
    
    public HashMap<String, ObservableList> getSpecificationsTypes(){
         return specificationTypes;
    }
    
    public void fillSpecificationsTypes(){     
        dbm.getSpeciafications(specificationTypes);
        String query = queryBuilder.getSpecificationValues(helpline.getName(), specificationTypes);
        dbm.getSpeciaficationsValues(query,specificationTypes);
    }
    
    public void adjustSpecification(String key, String value){
        // reset, add or adjust
        mySpecifications.put(key, value);
    }
    
    
//    public ObservableList getFunctionTypes(String key){
//        return specificationTypes.get(key);
//    }
    
    public void resetSpecifications(){
        mySpecifications.clear();
    }
}
