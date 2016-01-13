/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ChatServer.ChatClient;
import Server.ClientReceiving;
import cims.Employee;
import cims.Helpline;
import cims.Report;
import cims.Vehicle;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
public class CentralControllerFX extends controller.CentralController implements Initializable, Observer {

    @FXML private TextField tfApproachPolice;
    @FXML private Button btnSavePolice;
    @FXML private TextField tfApproachFirefighters;
    @FXML private Button btnSaveFirefighters;
    @FXML private TextField tfApproachAmbulance;
    @FXML private Button btnSaveAmbulance;

    @FXML private TableView<Vehicle> tvVehAssPolice;
    @FXML private TableView<Vehicle> tvVehAllPolice;
    @FXML private TableView<Employee> tvEmpAssPolice;
    @FXML private TableView<Employee> tvEmpAllPolice;
    @FXML private TableView<Vehicle> tvVehAssFire;
    @FXML private TableView<Vehicle> tvVehAllFire;
    @FXML private TableView<Employee> tvEmpAssFire;
    @FXML private TableView<Employee> tvEmpAllFire;
    @FXML private TableView<Vehicle> tvVehAssAmbulance;
    @FXML private TableView<Vehicle> tvVehAllAmbulance;
    @FXML private TableView<Employee> tvEmpAssAmbulance;
    @FXML private TableView<Employee> tvEmpAllAmbulance;
    @FXML private TableView<Report> tvIncidents;
    @FXML private Button btnInformationIncident;

    @FXML private TextArea taChat;
    @FXML private TextField tfChatMessage;
    
    @FXML private Label lblReportDate;
    @FXML private Label lblReportDesc;
    @FXML private Label lblReportLoc;
    @FXML private Label lblReportExtra;
    @FXML private Label lblReportWeather;
    
    @FXML private TabPane tpTabs;
    @FXML private Tab tptInfo;


    int tmpID = 0;

    private ChatClient cc = new ChatClient(1);

    private Thread chat;

    private ClientReceiving cr = new ClientReceiving();
    private Thread reportListener;

    private Report selectedReport;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeCollums();
        fillColums();
        makeEmployeeColumns();
        fillEmployeeColums();
        makeVehicleColumns();
        fillVehicleColumns();

        chat = new Thread(cc);
        reportListener = new Thread(cr);

        chat.setDaemon(true);
        reportListener.setDaemon(true);

        chat.start();
        cc.addObserver(this);

        reportListener.start();
        cr.addObserver(this);

    }

    public void savePolice(Event evnt) {
        System.out.println("doe iets");
        String plan = tfApproachPolice.getText();
        String helpline = "police";
        tfApproachPolice.clear();
        savePlan(plan, helpline, tmpID);
    }

    public void saveFirefighters(Event evnt) {
        System.out.println("doe iets");
        String plan = tfApproachFirefighters.getText();
        String helpline = "firefighters";
        tfApproachFirefighters.clear();
        savePlan(plan, helpline, tmpID);
    }

    public void saveAmbulance(Event evnt) {
        System.out.println("doe iets");
        String plan = tfApproachAmbulance.getText();
        String helpline = "ambulance";
        tfApproachAmbulance.clear();
        savePlan(plan, helpline, tmpID);
    }

    public void getAllVehicles() {

    }

    public void informationAccident() {
        System.out.println(selectedReport.toString());
        tpTabs.getSelectionModel().select(tptInfo);
        lblReportDate.setText(selectedReport.getStartDate().toString());
        lblReportDesc.setText(selectedReport.getDescription());
        lblReportExtra.setText(selectedReport.getExtraInformation());
        lblReportLoc.setText(selectedReport.getLocationGPS());
        lblReportWeather.setText(selectedReport.getWeather());
    }

    public void btnSendChatClick(Event e) {
        cc.setText("Centrale: " + tfChatMessage.getText());
    }

    public void makeCollums() 
    {
        tvIncidents.getColumns().clear();
        
        List<Field> fields = getCollumsReport();
        for (Field f : fields) {
            if (!f.getName().equals("dbm")) {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                if (f.getType().equals(ArrayList.class))
                {
                    tc.setCellValueFactory(new PropertyValueFactory<ArrayList<Helpline>, String>(f.getName()));
                }
                else
                {
                    tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                }
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvIncidents.getColumns().add(tc);
            }
        }

        tvIncidents.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvIncidents.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected report: " + newValue.getReportID());
                btnInformationIncident.setDisable(false);
                selectedReport = tvIncidents.getSelectionModel().getSelectedItem();
            } else {
                btnInformationIncident.setDisable(true);
            }
        });
    }
    
    public void makeEmployeeColumns()
    {
        tvEmpAllAmbulance.getColumns().clear();
        tvEmpAllFire.getColumns().clear();
        tvEmpAllPolice.getColumns().clear();
        
        List<Field> fieldsEmployees = getColumnsEmployee();
        for (Field f : fieldsEmployees){
            if (!f.getName().equals("helpline")){
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAllAmbulance.getColumns().add(tc);
            }
        }
        
        for (Field f : fieldsEmployees){
            if (!f.getName().equals("helpline")){
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAllFire.getColumns().add(tc);
            }
        }
        
        for (Field f : fieldsEmployees){
            if (!f.getName().equals("helpline")){
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAllPolice.getColumns().add(tc);
            }
        }
    }
    
    public void makeVehicleColumns()
    {
        tvVehAllAmbulance.getColumns().clear();
        tvVehAllFire.getColumns().clear();
        tvVehAllPolice.getColumns().clear();
        
        List<Field> fieldsVehicles = getColumnsVehicle();
        for (Field f : fieldsVehicles){
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAllAmbulance.getColumns().add(tc);
        }
        
        for (Field f : fieldsVehicles){
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAllFire.getColumns().add(tc);
        }
        
        for (Field f : fieldsVehicles){
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAllPolice.getColumns().add(tc);
        }
    }
    
    public void fillColums() {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        reports = fillIncidents();
        //reports.sort(cmprtr);
        tvIncidents.setItems(reports);
    }
    
    public void fillEmployeeColums()
    {
        ObservableList<Employee> employeesPolice = fillEmployees("Politie");
        tvEmpAllPolice.setItems(employeesPolice);
        
        ObservableList<Employee> employeesFire = fillEmployees("Brandweer");//FXCollections.observableArrayList();
        tvEmpAllFire.setItems(employeesFire);
        
        ObservableList<Employee> employeesAmbulance = fillEmployees("Ambulance");
        tvEmpAllAmbulance.setItems(employeesAmbulance);
    }
    
    public void fillVehicleColumns()
    {
        ObservableList<Vehicle> vehiclesPolice = fillVehicles("Politie");
        tvVehAllPolice.setItems(vehiclesPolice);
        
        ObservableList<Vehicle> vehiclesFire = fillVehicles("Brandweer");
        tvVehAllFire.setItems(vehiclesFire);
        
        ObservableList<Vehicle> vehiclesAmbulance = fillVehicles("Ambulance");
        tvVehAllAmbulance.setItems(vehiclesAmbulance);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass() == ClientReceiving.class) {

            super.loadHelplines();
            Platform.runLater(new Runnable() {

                @Override
                public void run() {

                    fillColums();
                }
            });
        } else if (o.getClass() == ChatClient.class) {
            taChat.setText(taChat.getText() + "\n" + ((ChatClient) o).getText());
        }
    }
}
