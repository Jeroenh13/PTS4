/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    
    public void savePolice(Event evnt)
    {
        System.out.println("doe iets");
        String plan = tfApproachPolice.getText();
        String helpline = "police";
        tfApproachPolice.clear();
        savePlan(plan, helpline);
    }
    
    public void saveFirefighters(Event evnt)
    {
        System.out.println("doe iets");
        String plan = tfApproachFirefighters.getText();
        String helpline = "firefighters";
        tfApproachFirefighters.clear();
        savePlan(plan, helpline);
    }
    
    public void saveAmbulance(Event evnt)
    {
        System.out.println("doe iets");
        String plan = tfApproachAmbulance.getText();
        String helpline = "ambulance";
        tfApproachAmbulance.clear();
        savePlan(plan, helpline);
    }
}
