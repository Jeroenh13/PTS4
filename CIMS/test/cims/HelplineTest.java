/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anna-May
 */
public class HelplineTest {
    Helpline helpline;
    Employee empDaniel;
    Employee empKlaas;
    Employee empLisa1;
    Employee empLisa2;
    Employee empLisa3;
    Employee empSaskia;
    Employee empNathalie;
    Employee empDanielBies;
    Report report1;
    Report report3;
    Report report4;
    Report report5;
    Report report6;
    Report report7;
    Report report8; 
    Report report9;
    Report report10;
    Report report11;
    
    HashMap<String, ObservableList> specificationTypes; 
    HashMap<String, String> mySpecifications; 
    
    DateTimeFormatter formatter;
    DateTimeFormatter formatter2;
    
    public HelplineTest() {
        helpline = new Helpline(1, "Politie");
        
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        report1 = new Report(5, "De buren hebben een feestje en de muziek staat veelste hard", "Geluidsoverlast", LocalDateTime.parse("2015-09-11 03:00:00.0", formatter), LocalDateTime.parse("2015-09-11 03:18:00.0", formatter));
        empDaniel = new Employee(4, "Daniel Bartrop", "Wijkagent", "1", "Wijk- en onthaal", "Gelderland-Midden", "Valkenswaard", "A", "C", report1, LocalDateTime.parse("2015-09-11 03:00:00.0", formatter), LocalDateTime.parse("2015-09-11 03:18:00.0", formatter));
        
        empKlaas = new Employee(11, "Klaas Jansen", "Rechercheur", "1", "Recherche", "Groningen", "Oirschot", "A", "C", null, null, null);
        
        report3 = new Report(4, "Er is ingebroken via het keukenraam", "Overval", LocalDateTime.parse("2015-09-11 21:00:00.0", formatter), LocalDateTime.parse("2015-09-11 22:33:00.0", formatter));
        report4 = new Report(7, "Er is ingebroken bij DSM Waalwijk", "Overval", LocalDateTime.parse("2015-10-14 15:41:51.0", formatter), null);
        report9 = new Report(7, "Er is ingebroken bij DSM Waalwijk", "Overval", LocalDateTime.parse("2015-10-14 15:41:51.0", formatter), null);
        report5 = new Report(1, "Auto tegen auto aan", "Verkeersongeval", LocalDateTime.parse("2015-10-01 12:15:00.0", formatter), LocalDateTime.parse("2015-10-01 15:30:00.0", formatter));
        empLisa1 = new Employee(16, "Lisa van den Berg", "Agent", "1", "Toezicht en interventie", "Groningen", "Valkenswaard", "A", "C", report3, LocalDateTime.parse("2015-09-11 21:00:00.0", formatter), LocalDateTime.parse("2015-09-11 22:33:00.0", formatter));
        empLisa2 = new Employee(16, "Lisa van den Berg", "Agent", "1", "Toezicht en interventie", "Groningen", "Valkenswaard", "A", "C", report4, LocalDateTime.parse("2015-10-14 15:41:51.0", formatter), null);
        empLisa3 = new Employee(16, "Lisa van den Berg", "Agent", "1", "Toezicht en interventie", "Groningen", "Valkenswaard", "A", "C", report5, LocalDateTime.parse("2015-10-01 12:15:00.0", formatter), LocalDateTime.parse("2015-10-01 15:30:00.0", formatter));
        empDanielBies = new Employee(15, "Daniel Biesrop", "Agent", "1", "Toezicht en interventie", "Noord-Holland Noord", "Valkenswaard", "A", "C", report4, LocalDateTime.parse("2015-10-14 15:41:51.0", formatter), null);
        
        report9.addEmployee(empDanielBies);
        report9.addEmployee(empLisa2);
        
        report6 = new Report(1, "Auto tegen auto aan", "Verkeersongeval", LocalDateTime.parse("2015-10-01 12:15:00.0", formatter), LocalDateTime.parse("2015-10-01 15:30:00.0", formatter));
        empSaskia = new Employee(12, "Saskia Versteegh", "Verkeersregelaar", "1", "Verkeer", "Groningen", "Geldrop", "A", "C", report6, LocalDateTime.parse("2015-10-01 12:15:00.0", formatter), LocalDateTime.parse("2015-10-01 15:30:00.0", formatter));
        
        empNathalie = new Employee(2, "Natalie Romanov", "Officier van dienst", "1", "Coördinator politieoperaties", "Twente", "Helmond", "B", "C", null, null, null);
        
        report7 = new Report(8, "Er is iemand gevallen van het. De buurman schreeuwde: Kom van dat dak af! Waarschuw niet meer! nenenenene kom van dat dak af! Hierna viel de man.", "Man van dak gevallen", LocalDateTime.parse("2016-01-06 11:22:45.0", formatter), null);
        report8 = new Report(9, "een niets vermoedend omatje is overvallen en wil aangiften doen", "tastjes dief", LocalDateTime.parse("2016-01-06 12:58:24.0", formatter), null);
        report10 = new Report(5, "De buren hebben een feestje en de muziek staat veelste hard", "Geluidsoverlast", LocalDateTime.parse("2015-09-11 03:00:00.0", formatter), LocalDateTime.parse("2015-09-11 03:18:00.0", formatter));
        report11 = new Report(6483, "De buren hebben een feestje en de muziek staat veelste hard", "Geluidsoverlast", LocalDateTime.parse("2015-09-11 03:00:00.0", formatter), LocalDateTime.parse("2015-09-11 03:18:00.0", formatter));
        
        specificationTypes = new HashMap();
        specificationTypes.put("badgenr", null);
        specificationTypes.put("name", null);
        specificationTypes.put("available", null);
        specificationTypes.put("function", null);
        specificationTypes.put("department", null);
        specificationTypes.put("region", null);
        specificationTypes.put("commune", null);
        specificationTypes.put("level", null);
        specificationTypes.put("startdate", null);
        specificationTypes.put("enddate", null);
        specificationTypes.put("reportid", null);
        specificationTypes.put("title", null);
        specificationTypes.put("team", null);
        specificationTypes.put("description", null);
        specificationTypes.put("reportstartdate", null);
        specificationTypes.put("reportenddate", null);
        
        mySpecifications = new HashMap();
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void TestSearchEmployeesOverview() {
        // search with name
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "Klaas Jansen", -1, "", null, null);
        assertFalse(helpline.getEmployees().contains(empDaniel));
        assertTrue(helpline.getEmployees().contains(empKlaas));
        assertFalse(helpline.getEmployees().contains(empLisa1));
        
        
        // search with badgenr
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "", 2, "", null, null);
        assertFalse(helpline.getEmployees().contains(empDaniel));
        assertTrue(helpline.getEmployees().contains(empNathalie));
        assertFalse(helpline.getEmployees().contains(empLisa1));
        
        // search with available
        mySpecifications.put("available", "1");
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "", -1, "", null, null);
        assertTrue(helpline.getEmployees().contains(empDaniel));
        assertTrue(helpline.getEmployees().contains(empKlaas));
        assertTrue(helpline.getEmployees().contains(empLisa1));
        assertTrue(helpline.getEmployees().contains(empLisa2));
        assertTrue(helpline.getEmployees().contains(empLisa3));
        assertTrue(helpline.getEmployees().contains(empSaskia));
        assertTrue(helpline.getEmployees().contains(empNathalie));
        
        // search with function
        mySpecifications.clear();
        mySpecifications.put("function", "Agent");
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "", -1, "", null, null);
        assertTrue(helpline.getEmployees().contains(empLisa2));
        assertFalse(helpline.getEmployees().contains(empNathalie));
        
        // search with department
        mySpecifications.clear();
        mySpecifications.put("department", "Verkeer");
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "", -1, "", null, null);
        assertTrue(helpline.getEmployees().contains(empSaskia));
        assertFalse(helpline.getEmployees().contains(empNathalie));
        
        // search with region 
        mySpecifications.clear();
        mySpecifications.put("region", "Groningen");
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "", -1, "", null, null);
        assertFalse(helpline.getEmployees().contains(empDaniel));
        assertTrue(helpline.getEmployees().contains(empKlaas));
        assertTrue(helpline.getEmployees().contains(empLisa1));
        assertTrue(helpline.getEmployees().contains(empLisa2));
        assertTrue(helpline.getEmployees().contains(empLisa3));
        assertTrue(helpline.getEmployees().contains(empSaskia));
        
        // search with commune
        mySpecifications.clear();
        mySpecifications.put("commune", "Helmond");
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "", -1, "", null, null);
        assertTrue(helpline.getEmployees().contains(empNathalie));
        assertFalse(helpline.getEmployees().contains(empDaniel));
        
        // search with level
        mySpecifications.clear();
        mySpecifications.put("level", "B");
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "", -1, "", null, null);
        assertTrue(helpline.getEmployees().contains(empNathalie));
        assertFalse(helpline.getEmployees().contains(empDaniel));
        
        // search with team
        mySpecifications.clear();
        mySpecifications.put("team", "C");
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "", -1, "", null, null);
        assertTrue(helpline.getEmployees().contains(empDaniel));
        assertTrue(helpline.getEmployees().contains(empKlaas));
        assertTrue(helpline.getEmployees().contains(empLisa1));
        assertTrue(helpline.getEmployees().contains(empLisa2));
        assertTrue(helpline.getEmployees().contains(empLisa3));
        assertTrue(helpline.getEmployees().contains(empSaskia));
        assertTrue(helpline.getEmployees().contains(empNathalie));
        
        // search with only startdate
        mySpecifications.clear();
        LocalDate start = LocalDate.parse("2015-10-01", formatter2);
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "Lisa van den Berg", -1, "", start, null);
        assertFalse(helpline.getEmployees().contains(empDaniel));
        assertFalse(helpline.getEmployees().contains(empLisa1));
        assertTrue(helpline.getEmployees().contains(empLisa2));
        assertTrue(helpline.getEmployees().contains(empLisa3));
        
        // search with only enddate
        LocalDate end = LocalDate.parse("2015-10-01", formatter2);
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "Lisa van den Berg", -1, "", null , end);
        assertFalse(helpline.getEmployees().contains(empDaniel));
        assertTrue(helpline.getEmployees().contains(empLisa1));
        assertFalse(helpline.getEmployees().contains(empLisa2));
        assertFalse(helpline.getEmployees().contains(empLisa3));
        
        // search with start and enddate
        start = LocalDate.parse("2015-09-11", formatter2);
        end = LocalDate.parse("2015-10-02", formatter2);
        helpline.searchEmployees(specificationTypes, mySpecifications, false, "Lisa van den Berg", -1, "", start , end);
        assertFalse(helpline.getEmployees().contains(empDaniel));
        assertTrue(helpline.getEmployees().contains(empLisa1));
        assertTrue(helpline.getEmployees().contains(empLisa2));
        assertTrue(helpline.getEmployees().contains(empLisa3));
    }
    
    @Test
    public void TestSearchEmployeesAssign() {
        // search with name
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "Klaas Jansen", -1, "", null, null);
        assertFalse(helpline.getEmployeesAss().contains(empDaniel));
        Employee daniel2 = helpline.getEmployeeWithID(empDaniel.getBadgeNR()); 
        assertNull(daniel2);
        assertTrue(helpline.getEmployeesAss().contains(empKlaas));
        
        // search with name lisa, has finished reports
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "Lisa van den Berg", -1, "", null, null);
        assertFalse(helpline.getEmployeesAss().contains(empDaniel));
        daniel2 = helpline.getEmployeeWithID(empDaniel.getBadgeNR()); 
        assertNull(daniel2);
        assertFalse(helpline.getEmployeesAss().contains(this.empLisa1));
        assertTrue(helpline.getEmployeesAss().contains(this.empLisa2));
        assertFalse(helpline.getEmployeesAss().contains(this.empLisa3));
        
        // search with badgenr
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", 2, "", null, null);
        assertFalse(helpline.getEmployeesAss().contains(empDaniel));
        daniel2 = helpline.getEmployeeWithID(empDaniel.getBadgeNR()); 
        assertNull(daniel2);
        assertTrue(helpline.getEmployeesAss().contains(empNathalie));
        
        // search with available
        mySpecifications.put("available", "1");
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        daniel2 = helpline.getEmployeeWithID(empDaniel.getBadgeNR());
        Employee saskia2 = helpline.getEmployeeWithID(this.empSaskia.getBadgeNR());
        assertNotNull(saskia2);
        assertNotNull(daniel2);
        assertTrue(helpline.getEmployeesAss().contains(empKlaas));
        assertTrue(helpline.getEmployeesAss().contains(empLisa2));
        assertTrue(helpline.getEmployeesAss().contains(empNathalie));
        
        // search with function
        mySpecifications.clear();
        mySpecifications.put("function", "Agent");
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        assertTrue(helpline.getEmployeesAss().contains(empLisa2));
        assertFalse(helpline.getEmployeesAss().contains(empNathalie));
        
        // search with department
        mySpecifications.clear();
        mySpecifications.put("department", "Verkeer");
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        assertTrue(helpline.getEmployeesAss().contains(saskia2));
        assertFalse(helpline.getEmployeesAss().contains(empNathalie));
        
        // search with region 
        mySpecifications.clear();
        mySpecifications.put("region", "Groningen");
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        assertFalse(helpline.getEmployeesAss().contains(daniel2));
        assertTrue(helpline.getEmployeesAss().contains(empKlaas));
        assertTrue(helpline.getEmployeesAss().contains(empLisa2));
        assertTrue(helpline.getEmployeesAss().contains(saskia2));
        
        // search with commune
        mySpecifications.clear();
        mySpecifications.put("commune", "Helmond");
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        assertTrue(helpline.getEmployeesAss().contains(empNathalie));
        assertFalse(helpline.getEmployeesAss().contains(daniel2));
        
        // search with level
        mySpecifications.clear();
        mySpecifications.put("level", "B");
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        assertTrue(helpline.getEmployeesAss().contains(empNathalie));
        assertFalse(helpline.getEmployeesAss().contains(daniel2));
        
        // search with team
        mySpecifications.clear();
        mySpecifications.put("team", "C");
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        assertTrue(helpline.getEmployeesAss().contains(daniel2));
        assertTrue(helpline.getEmployeesAss().contains(empKlaas));
        assertTrue(helpline.getEmployeesAss().contains(empLisa2));
        assertTrue(helpline.getEmployeesAss().contains(saskia2));
        assertTrue(helpline.getEmployeesAss().contains(empNathalie));
    }
    
    @Test
    public void TestGetIncidents() {
        // reports die nog open staan
        // report9 7, 8
        this.mySpecifications.clear();
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        
        helpline.getIncidents();
        ObservableList<Report> reports = helpline.getReports();
        
        // reports without persons bound with it
        assertTrue(reports.contains(report7));
        assertTrue(reports.contains(report8));
        
        // reports with persons bound with it
        assertTrue(reports.contains(report9));
    }
    
    
    @Test
    public void TestGetEmployeeWithID() {
        helpline.searchEmployees(specificationTypes, mySpecifications, true, "", -1, "", null, null);
        Employee emp = helpline.getEmployeeWithID(empDaniel.getBadgeNR());
        assertThat(emp, is(not(empDaniel)));
        
        Employee empL = helpline.getEmployeeWithID(empLisa2.getBadgeNR());
        assertEquals(empL, empLisa2);
        assertThat(empL, is(not(empLisa1)));
    }
    
    @Test
    public void TestLoadAllEmployees() {
        ObservableList<Employee> employees;
        helpline.loadAllEmployees();
        employees = helpline.getEmployees();
        
        // kijken of dat hij de goeie Daniel bevat
        empDanielBies.setHelpline(helpline); 
        assertTrue(employees.contains(this.empDanielBies)); 
        empDanielBies.setHelpline(null);
        // kijken of hij de goeie lisa bevat
        empLisa2.setHelpline(helpline); 
        assertTrue(employees.contains(this.empLisa2));
        empLisa2.setHelpline(null); 
        // kijken of hij de foute lisa bevat
        empLisa1.setHelpline(helpline); 
        assertFalse(employees.contains(this.empLisa1));
        empLisa1.setHelpline(null);
    }
    
    @Test
    public void TestLoadAllReports() {
        ObservableList<Report> reports;
        helpline.loadAllEmployees();
        reports = helpline.getReports();
        
        // kijken of report die nog niet is afgesloten erin zit
        assertTrue(reports.contains(this.report9));
        // kijken of report die al is afgesloten erin zit
        assertFalse(reports.contains(this.report10));
    }
    
//    @Test
//    public void TestLoadAllVehicles() {
//        ObservableList<Vehicle> vehicles;
//        helpline.loadAllVehicles();
//        vehicles = helpline.getVehicles();
//        
//        // kijken of er een vehicle in zit die nog niet afgegeven is
//        assertTrue(vehicles.contains(this.report9));
//        // kijken of er een vehicle in zit die nog al afgegeven is
//        assertFalse(vehicles.contains(this.report10));
//    }
    
    @Test
    public void TestGetReportWithID() {
        helpline.loadAllReports();
        
        // kijken of report die nog niet is afgesloten erin zit
        assertEquals(helpline.getReportWithID(report9.getReportID()), this.report9);
        // kijken of report die al is afgesloten erin zit
        assertThat(helpline.getReportWithID(report10.getReportID()), is(not(report10)));
    }
    
//    @Test
//    public void TestGetVehicleWithID() {
//        helpline.loadAllVehicles();
//        
//        // kijken of report die nog niet is afgesloten erin zit
//        assertEquals(helpline.getVehicleWithID(report9.getReportID()), this.report9);
//        // kijken of report die al is afgesloten erin zit
//        assertThat(helpline.getVehicleWithID(report10.getReportID()), is(not(report10)));
//    }
    
    @Test
    public void TestAddReport() {
        ObservableList<Report> reports;
        helpline.loadAllReports();
        reports = helpline.getReports();
        
        assertFalse(reports.contains(report11));
        
        helpline.addReport(report11); 
        
        assertTrue(reports.contains(report11));
    }
    
    @Test
    public void TestbindReportsToEmployees() {
        ObservableList<Report> reports;
        ObservableList<Employee> employees;
        helpline.loadAllReports();
        helpline.loadAllEmployees();
        helpline.bindReportsToEmployees();
        reports = helpline.getReports();
        employees = helpline.getEmployees();
        
        
    }
    
//    @Test
//    public void TestBindVehiclesToEmployees() {
//        ObservableList<Vehicle> assignedVehicles;
//        helpline.loadAllEmployees();
//        helpline.loadAllVehicles();
//        List<PlannedVehicle> planning; // deze gevuld boven aan maken
//        helpline.bindVehiclesToEmployees(null);
//    }
}
