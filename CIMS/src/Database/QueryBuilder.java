/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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
    public static String searchOverview(HashMap hmOverviewValues){
        String queryGetPersonsOv = "";
        return null;
    } 
    
    public static String searchAssign(HashMap hmAssignValues){
        String queryGetPersonsAss = "SELECT * FROM vwEmployeeIncident";
        
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
    
    //the missing method
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
