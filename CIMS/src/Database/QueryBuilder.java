/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import javafx.collections.ObservableList;

/**
 *
 * @author Anna-May
 */
public final class QueryBuilder {
    public static String search(HashMap<String, String> hmValues, String name, int badgeNr, String incident, LocalDate fromDate, LocalDate tillDate){
        String queryGetPersons = "SELECT * FROM vwemployees WHERE ";
        
        boolean first = true;
        for (Map.Entry<String, String> entry : hmValues.entrySet()){
            if(first == false){
                queryGetPersons+= " AND ";
            }else{
                first = false;
            }
            queryGetPersons += entry.getKey() + " = '"+ entry.getValue() + "'";
        }
        
        if(!name.equals("")){
            if(first == false){
                queryGetPersons+= " AND ";
            }else{
                first = false;
            }
            queryGetPersons += "Name ='" + name + "' ";
        }
        if(!incident.equals("")){
            if(first == false){
                queryGetPersons+= " AND ";
            }else{
                first = false;
            }
            queryGetPersons += "Title ='" + incident + "' ";
        }
        if(badgeNr != -1){
            if(first == false){
                queryGetPersons+= " AND ";
            }else{
                first = false;
            }
            queryGetPersons += "BadgeNR =" + badgeNr + " ";
        }
//        if(fromDate != null && tillDate != null){
//            queryGetPersons += "AND Name =" + name + " ";
//        }
        
        
        
        queryGetPersons += ";";
        
        return queryGetPersons;
    } 
    
    public static String searchWithoutDate(HashMap hmAssignValues){
        String queryGetPersonsAss = "SELECT * FROM vwemployees";
        
        if(!hmAssignValues.isEmpty()){
            queryGetPersonsAss+= " WHERE ";
            
            Iterator it = hmAssignValues.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                queryGetPersonsAss +="AND " + entry.getKey() + " = " + entry.getValue();
                //it.remove(); // avoids a ConcurrentModificationException
            }
        }

        queryGetPersonsAss += ";";
        
        return queryGetPersonsAss;
    }

    public String getSpecificationValues(String helpline, HashMap<String, ObservableList> specifications){
        String querySpecValues = "SELECT ";
        boolean first = true;
        for (Map.Entry<String, ObservableList> entry : specifications.entrySet()){
            if(entry.getValue() != null){
                if(first == false){
                    querySpecValues+= ",";
                }else{
                    first = false;
                }
                querySpecValues += " " + entry.getKey();
            }
        }

        querySpecValues += " FROM vwemployees WHERE Helpline = '" + helpline + "';";
        
        return querySpecValues;
    } 
}
