/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ChatServer.ChatClient;
import Server.ClientReceiving;
import cims.Helpline;
import cims.Report;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 *
 * @author kitty
 */
public class CentralControllerFX extends controller.CentralController implements Initializable, Observer
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
    
    @FXML
    private TextArea taChat;
    @FXML
    private TextField tfChatMessage;
    
    int tmpID = 0;
    
    private ChatClient cc = new ChatClient(1);

    private Thread chat;
    
    
    private ClientReceiving cr = new ClientReceiving();
    private Thread reportListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeCollums();
        fillColums();
        
        chat = new Thread(cc);
        chat.start();
        cc.addObserver(this);
        
        
        reportListener = new Thread(cr);
        reportListener.start();
        cr.addObserver(this);
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
    
    public void btnSendChatClick(Event e) {
        cc.setText("Centrale: " + tfChatMessage.getText());
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
    }
    
    public void fillColums()
    {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        reports = fillIncidents();
        tvIncidents.setItems(reports);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o.getClass() == ClientReceiving.class)
        {
            super.loadHelplines();
            fillColums();
            System.out.println("/yay");
        }
        else if(o.getClass() == ChatClient.class)
        {
            taChat.setText(taChat.getText() + "\n" + ((ChatClient)o).getText());
        }
    }
}
