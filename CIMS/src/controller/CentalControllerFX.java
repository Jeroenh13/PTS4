/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cims.Helpline;
import cims.Report;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author kitty
 */
public class CentalControllerFX extends controller.CentralController implements Initializable
{
    
    @FXML
    private TextField tfApproachPolice;
    @FXML
    private Button btnSavePolice;
    @FXML
    private TextField tfApproachFirefighters;
    @FXML
    private Button btnSaveFirefighters;
    @FXML 
    private TextField tfApproachAmbulance;
    @FXML
    private Button btnSaveAmbulance;
    
    @FXML
    private TableView tvVehAssPolice;
    @FXML
    private TableView tvVehAllPolice;
    @FXML
    private TableView tvVehAssFire;
    @FXML
    private TableView tvVehAllFire;
    @FXML
    private TableView tvVehAssAmbulance;
    @FXML
    private TableView tvVehAllAmbulance;
    @FXML 
    private TableView<Report> tvIncidents;
    @FXML private Button btnInformationIncident;
    
    int tmpID = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeCollums();
    }
    
    public void savePolice(Event evnt)
    {
        System.out.println("doe iets");
        String plan = tfApproachPolice.getText();
        String helpline = "police";
        tfApproachPolice.clear();
        savePlan(plan, helpline, tmpID);
    }
    
    public void saveFirefighters(Event evnt)
    {
        System.out.println("doe iets");
        String plan = tfApproachFirefighters.getText();
        String helpline = "firefighters";
        tfApproachFirefighters.clear();
        savePlan(plan, helpline, tmpID);
    }
    
    public void saveAmbulance(Event evnt)
    {
        System.out.println("doe iets");
        String plan = tfApproachAmbulance.getText();
        String helpline = "ambulance";
        tfApproachAmbulance.clear();
        savePlan(plan, helpline, tmpID);
    }
    
    public void getAllVehicles()
    {
        
    }
    
    public void informationAccident()
    {
        
    }
    
    public void makeCollums()
    {
        tvIncidents.getColumns().clear();
        List<Field> fields = getCollumsReport();
        for (Field f : fields)
        {
            if (!f.getName().equals("dbm"))
            {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                int width = 135;
                tc.setMinWidth(width);
                tvIncidents.getColumns().add(tc);
            }
        }
        List<Helpline> helplines = getHelplines();
        ObservableList<Report> reports = FXCollections.observableArrayList();
        for (Helpline h : helplines)
        {
            ObservableList<Report> tempreports = FXCollections.observableArrayList();
            for (Report r : h.getReports())
            {
                System.out.println(r.getReportID() + "---" +  r.getTitle() + "---" +  r.getDescription());
                tempreports.add(r);
            }
            System.out.println(tempreports.size());
            reports.addAll(tempreports);
        }
        tvIncidents.setItems(reports);
    }
}
