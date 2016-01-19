/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseManager;
import cims.Employee;
import cims.Helpline;
import cims.PlannedVehicle;
import cims.Report;
import cims.Vehicle;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author kitty
 */
public class CentralController  {

    private final DatabaseManager dbm;
    private List<Helpline> helplines = new ArrayList<>();
    private ObservableList<Report> reports = FXCollections.observableArrayList();
    private List<PlannedVehicle> vehiclePlanning;
    
    
    public CentralController() {
        this.dbm = new DatabaseManager();
        loadHelplines();
    }

    public List<Helpline> getHelplines() {
        return helplines;
    }
    
    public Helpline getHelplineByID(int id){
        for(Helpline h : helplines){
            if(h.getID() == id)
                return h;
        }
        
        return null;
    }

    public boolean saveApproach(String approach, String helpline, int reportID) {
        boolean succes = false;
        int helplineID = 0;
        for (Helpline h : helplines) {
            if (h.getName().equals(helpline)) {
                helplineID = h.getID();
            }
        }
        int helplinereportID = dbm.getApproachID(helplineID, reportID);
        if (helplinereportID != -1)
            succes = dbm.saveApproach(helplinereportID, approach, helplineID, reportID);
        System.out.println(helplinereportID);
        return succes;
    }

    public void loadHelplines() {
        List<Report> dbreports = new ArrayList();
        helplines = dbm.getHelpLines();

        for (Helpline h : helplines) {
            h.loadAllEmployees();
            h.loadAllVehicles();

            dbreports.addAll(dbm.getAllReports(h.getName()));
            
            for (Report r : dbreports) {
                Report report = reportExists(r);
                if (report != null) {
                    h.addReport(report);
                    report.addHelpline(h);
                } else {
                    reports.add(r);
                    h.addReport(r);
                    r.addHelpline(h);
                }
            }

            h.bindReportsToEmployees();

            dbreports.clear();
        }
    }

    private Report reportExists(Report r) {
        for (Report rep : reports) {
            if (rep.getReportID() == r.getReportID()) {
                return rep;
            }
        }
        return null;
    }

    public List<Field> getCollumsReport() {
        List fieldsList = new ArrayList<>();
        Class r = Report.class;
        Field[] fields = r.getDeclaredFields();
        //System.out.println(fields.toString());
        //System.out.println(fields.length);
        for (Field f : fields) {
            //System.out.println(f.getName());
            fieldsList.add(f);
        }
        return fieldsList;
    }
    
    public List<Field> getColumnsEmployee()
    {
        List fieldList = new ArrayList<>();
        Class e = Employee.class;
        Field[] fields = e.getDeclaredFields();
        for (Field f : fields)
        {
            fieldList.add(f);
        }
        return fieldList;
    }
    
    public List<Field> getColumnsVehicle()
    {
        List fieldList = new ArrayList<>();
        Class v = Vehicle.class;
        Field[] fields = v.getDeclaredFields();
        for (Field f : fields)
        {
            fieldList.add(f);
        }
        return fieldList;
    }

    public ObservableList<Report> fillIncidents() {
//        ObservableList<Report> reps = FXCollections.observableArrayList();
//        List<Helpline> helplines = getHelplines();
//        for (Helpline h : helplines) {
//            ObservableList<Report> tempreports = FXCollections.observableArrayList();
//            for (Report r : h.getReports()) {
//                tempreports.add(r);
//            }
//            reps.addAll(tempreports);
//        }
//        SortedList<Report> sortedList = new SortedList<>(reports,
//        (Report r1, Report r2) -> 
//        {
//            if (r1.getReportID() < r2.getReportID())
//            {
//                return -1;
//            }
//            else if (r1.getReportID() > r2.getReportID())
//            {
//                return 1;
//            }
//            else
//            {
//                return 0;
//            }
//        });

        ObservableList<Report> reps = FXCollections.observableArrayList();
        for (Report r : reports)
        {
            if (r.getEndDate() == null)
            {
                reps.add(r);
            }
        }

        return reps;
    }
    
    public ObservableList<Employee> fillEmployees(String helpline)
    {
        ObservableList<Employee> emps = null ;//= FXCollections.observableArrayList();
        for (Helpline h : helplines)
        {
            if (h.getName().equals(helpline))
            {
                emps = h.getEmployees();
            }
        }
        return emps;
    }
    
    public ObservableList<Vehicle> fillVehicles(String helpline)
    {
        ObservableList<Vehicle> vehs = null;
        for (Helpline h : helplines)
        {
            if (h.getName().equals(helpline))
            {
                vehs = h.getVehicles();
                for (Vehicle v : vehs)
                {
                    System.out.println(h.getName() + " --- " + v.getId());
                }
            }
        }
        return vehs;
    }
    
    public String getApproachReport(int reportID, int helplineID)
    {
        String approach = dbm.getApproachHelpline(reportID, helplineID);
        return approach;
    }
    
    public boolean closeReport(int reportID, LocalDateTime date)
    {
        return dbm.closeReport(reportID, date);
    }
    
    public void loadVehiclePlanning()
    {
        vehiclePlanning = dbm.getVehiclePlanning();
    }
}

