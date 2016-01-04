/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 *
 * @author kitty
 */
public class CentalControllerFX extends CentralController {
    
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
    
    int tmpID = 0;
    
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
}
