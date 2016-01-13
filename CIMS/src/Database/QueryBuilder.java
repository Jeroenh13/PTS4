/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import cims.Employee;
import cims.Report;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    public static String search(boolean ass, HashMap<String, String> hmValues, String name, int badgeNr, String incident, LocalDate fromDate, LocalDate tillDate, String helpline){
        String view;
        if(ass == true){
            view = "vwemployeeass";
        }else{
            view = "vwemployee";
        }
        
        String queryGetPersons = "SELECT * FROM " + view + " WHERE helpline = '" + helpline + "'"; 
        
        for (Map.Entry<String, String> entry : hmValues.entrySet()){
            if(!entry.getValue().equals("no selection")){
                queryGetPersons += " AND ";
                queryGetPersons += entry.getKey() + " = '"+ entry.getValue() + "'";
            }
        }
        
        if(!name.equals("")){
            queryGetPersons+= " AND ";
            queryGetPersons += "name ='" + name + "' ";
        }
        if(!incident.equals("")){
            queryGetPersons+= " AND ";
            queryGetPersons += "title ='" + incident + "' ";
        }
        if(badgeNr != -1){
            queryGetPersons+= " AND ";
            queryGetPersons += "badgenr =" + badgeNr + " ";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
        LocalDateTime start;
        LocalDateTime end;
        if(fromDate != null && tillDate != null){
            // periode er tussen
            start = LocalDateTime.of(fromDate, LocalTime.of(0,0,0));
            end = LocalDateTime.of(tillDate, LocalTime.of(0,0,0));
//            SELECT * FROM vwemployee 
//WHERE helpline = 'Politie'
//AND start >= STR_TO_DATE('2015-09-11','%Y-%c-%e %T') AND end <= STR_TO_DATE('2015-09-31','%Y-%c-%e %T');
            queryGetPersons += "AND startdate >= STR_TO_DATE('" + start.format(formatter) + "','%Y-%c-%e %T') AND (enddate <= STR_TO_DATE('" + end.format(formatter) + "','%Y-%c-%e %T') OR enddate IS NULL)";
        }else if(fromDate == null && tillDate != null){
            // maand voor tilldate
            end = LocalDateTime.of(tillDate, LocalTime.of(0,0,0));
            start = end.minusMonths(3);
            queryGetPersons += "AND startdate >= STR_TO_DATE('" + start.format(formatter) + "','%Y-%c-%e %T') AND enddate <= STR_TO_DATE('" + end.format(formatter) + "','%Y-%c-%e %T')"; 
        }else if(fromDate != null && tillDate == null){
            // start tot sysdate
            start = LocalDateTime.of(fromDate, LocalTime.of(0,0,0));
            //LocalDateTime now = LocalDateTime.now();
            queryGetPersons += "AND startdate >= STR_TO_DATE('" + start.format(formatter) + "','%Y-%c-%e %T') AND (enddate <= " + "SYSDATE() OR enddate IS NULL)";
        }
        
        queryGetPersons += ";";
        
        return queryGetPersons;
    } 

    public static String getSpecificationValues(String helpline, HashMap<String, ObservableList> specifications){
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

        querySpecValues += " FROM vwemployee WHERE Helpline = '" + helpline + "';";
        
        return querySpecValues;
    }
    
    public static String getIncidentsHelpline(String helpline){
        String query = "SELECT DISTINCT reportID ,title, helpline, description, ReportStartDate FROM vwEmployeeAss WHERE helpline = '"+ helpline +"' AND ReportStartDate is not null;";
        return query;
    }
    
    public static String getNewIncidentsHelpline(String helpline){
        String query = "SELECT reportID ,title, description, startdate FROM vwhelplinereport WHERE helpline = '"+ helpline + "' and enddate is null;";
        return query;
    }
}
