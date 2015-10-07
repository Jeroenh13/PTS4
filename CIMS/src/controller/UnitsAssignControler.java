/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cims.Employee;
import cims.Helpline;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Anna-May
 */
public class UnitsAssignControler {
    private Helpline unit;
    private HashMap<String, ObservableList> specificationTypes;
    private HashMap<String, String> specifications;
    
    public UnitsAssignControler(){
        specifications = new HashMap<>();
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
        return unit.searchEmployees(specifications);
    }
    
    public void getTypeSpecifications(){
        //TO DO 
        //vraag alle specificatie types aan het database voor de desbetreffende unit
        //vul de hashmap hier meer met values, "Kies een optie" en de mogelijke waardes
    }
    
    public void addSpecification(String key, String value){
        specifications.put(key, value);
    }
    
    public ObservableList getFunctionTypes(String key){
        return specificationTypes.get(key);
    }
    
    public void resetSpecifications(){
        specifications.clear();
    }
}
