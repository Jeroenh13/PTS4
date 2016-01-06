/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.DatabaseManager;
import Server.ClientReceiving;
import cims.Helpline;
import cims.Report;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.util.Callback;

/**
 *
 * @author kitty
 */
public class CentralController  {

    private final DatabaseManager dbm;
    private List<Helpline> helplines = new ArrayList<>();
    private ObservableList<Report> reports = FXCollections.observableArrayList();

    
    
    public CentralController() {
        this.dbm = new DatabaseManager();
        loadHelplines();
    }

    public List<Helpline> getHelplines() {
        return helplines;
    }

    public void savePlan(String approach, String helpline, int reportID) {
        int helplineID = 0;
        for (Helpline h : helplines) {
            if (h.getName().equals(helpline)) {
                helplineID = h.getID();
            }
        }
        dbm.saveApproach(approach, helplineID, reportID);
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
        return reports;
    }
}
