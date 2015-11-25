/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import cims.Employee;
import cims.Report;
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
            queryGetPersons += "Name ='" + name + "' ";
        }
        if(!incident.equals("")){
            queryGetPersons+= " AND ";
            queryGetPersons += "Title ='" + incident + "' ";
        }
        if(badgeNr != -1){
            queryGetPersons+= " AND ";
            queryGetPersons += "BadgeNR =" + badgeNr + " ";
        }
//        if(fromDate != null && tillDate != null){
//            queryGetPersons += "AND Name =" + name + " ";
//        }
        
        queryGetPersons += ";";
        
        return queryGetPersons;
    } 
    
//    public static String searchWithoutDate(HashMap hmAssignValues){
//        String queryGetPersonsAss = "SELECT * FROM vwemployee";
//        
//        if(!hmAssignValues.isEmpty()){
//            queryGetPersonsAss+= " WHERE ";
//            
//            Iterator it = hmAssignValues.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry entry = (Map.Entry)it.next();
//                queryGetPersonsAss +="AND " + entry.getKey() + " = " + entry.getValue();
//                //it.remove(); // avoids a ConcurrentModificationException
//            }
//        }
//
//        queryGetPersonsAss += ";";
//        
//        return queryGetPersonsAss;
//    }

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
        String query = "SELECT DISTINCT reportID ,title, name, description, start FROM vwnewreport WHERE name = '"+ helpline +"';";
        return query;
    }
    
//    public static ArrayList<String> saveEmpsForReport(Report report, ObservableList<Employee> employees){
//        ArrayList<String> queries = new ArrayList<>();
//        boolean found = false;
//        
//        for(Employee emp: employees){
//            for(Employee empR: report.getEmployees()){
//                if(empR.getBadgeNR() == emp.getBadgeNR()){
//                    found = true;
//                    queries.add("UPDATE helplinevehiclereport SET EmpFromDate ="+ emp.getStart() +" , EmpTillDate ="+ emp.getEnd() +" WHERE EmployeeID ="+ emp.getBadgeNR() +" AND ReportID = " + report.getReportID()+ ";");
//                }
//            }
//            
//            if(found == false){
//                queries.add("INSERT INTO helplinevehiclereport (EmployeeID, ReportID, EmpFromDate, EmpTillDate) VALUES (" + emp.getBadgeNR() + ", " + report.getReportID() + ", " + emp.getStart() +", "+emp.getEnd()+ ");");
//            }
//            
//            found = false;
//        }
//        return queries;
//    }
}
