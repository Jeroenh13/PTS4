/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Anna-May
 */
public final class queryBuilder {
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
}
