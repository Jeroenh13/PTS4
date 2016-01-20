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
import cims.cbItem;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author kitty
 */
public class CentralControllerFX extends controller.CentralController implements Initializable, Observer {

    @FXML
    private TextArea tfApproachPolice;
    @FXML
    private Button btnSavePolice;
    @FXML
    private TextArea tfApproachFirefighters;
    @FXML
    private Button btnSaveFirefighters;
    @FXML
    private TextArea tfApproachAmbulance;
    @FXML
    private Button btnSaveAmbulance;
    @FXML
    private Button btnCloseReport;
    
    /* Search Controls */
    
    @FXML
    private Button cgSearchButton;
    @FXML
    private Button cgSearchAddLineButton;
    @FXML
    private TextField cgSearchName;
    @FXML
    private DatePicker cgSearchDateStart;
    @FXML
    private DatePicker cgSearchDateEnd;
    @FXML
    private ComboBox cgSearchComboLines;
    @FXML
    private ListView cgSearchSelectedLines;
    
    /* End of Search Controls */
    

    @FXML
    private TableView<Vehicle> tvVehAssPolice;
    @FXML
    private TableView<Vehicle> tvVehAllPolice;
    @FXML
    private TableView<Employee> tvEmpAssPolice;
    @FXML
    private TableView<Employee> tvEmpAllPolice;
    @FXML
    private TableView<Vehicle> tvVehAssFire;
    @FXML
    private TableView<Vehicle> tvVehAllFire;
    @FXML
    private TableView<Employee> tvEmpAssFire;
    @FXML
    private TableView<Employee> tvEmpAllFire;
    @FXML
    private TableView<Vehicle> tvVehAssAmbulance;
    @FXML
    private TableView<Vehicle> tvVehAllAmbulance;
    @FXML
    private TableView<Employee> tvEmpAssAmbulance;
    @FXML
    private TableView<Employee> tvEmpAllAmbulance;
    @FXML
    private TableView<Report> tvIncidents;

    @FXML
    private Button btnInformationIncident;
    
    @FXML
    private Button btnAssignEmpPol;
    @FXML
    private Button btnRemoveEmpPol;
    @FXML
    private Button btnAssignEmpFire;
    @FXML
    private Button btnRemoveEmpFire;
    @FXML
    private Button btnAssignEmpAmbu;
    @FXML
    private Button btnRemoveEmpAmbu;
    
    @FXML
    private Button btnAssignPolVehicle;
    @FXML
    private Button btnRemovePolVehicle;
    @FXML
    private Button btnAssignFireVehicle;
    @FXML
    private Button btnRemoveFireVehicle;
    @FXML
    private Button btnAssignAmbuVehicle;
    @FXML
    private Button btnRemoveAmbuVehicle;

    @FXML
    private TextArea taChat;
    @FXML
    private TextField tfChatMessage;

    @FXML
    private Label lblReportDate;
    @FXML
    private Label lblReportDesc;
    @FXML
    private Label lblReportLoc;
    @FXML
    private Label lblReportExtra;
    @FXML
    private Label lblReportWeather;

    @FXML
    private TabPane tpTabs;
    @FXML
    private Tab tptInfo;

    @FXML
    private ListView<cbItem> lvHelplines;
    @FXML
    private Button btnAddHelpline;
    @FXML
    private ComboBox cbHelplines;

    private ChatClient cc = null;
    private Thread chat;
    private ClientReceiving cr = new ClientReceiving();
    private Thread reportListener;

    private Report selectedReport;
    private Employee selectedEmployee;
    private Vehicle selectedVehicle;
    
    private ObservableList<Helpline> hLines = FXCollections.observableArrayList();
    
    
    private final ObservableList<cbItem> listItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeCollums();
        fillColums();
        makeEmployeeColumns();
        fillEmployeeColums();
        makeVehicleColumns();
        fillVehicleColumns();
        
        loadSearchComboLines();

        reportListener = new Thread(cr);

        reportListener.setDaemon(true);

        reportListener.start();
        cr.addObserver(this);
        
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lvHelplines.setItems(listItems);
                Helpline helpline = new Helpline();
                helpline.getLines().stream().forEach((help) -> {
                    cbHelplines.getItems().add(new cbItem(help.getID(), help.getName()));
                });
            }
        });

    }
    
    public void loadSearchComboLines(){
        
        for(Helpline h : getHelplines()){
            cgSearchComboLines.getItems().add(h.getName());
            cgSearchSelectedLines.getItems().add(h.getName());
        }
    }
    
    public void addSearchComboLine(){
        String selected = cgSearchComboLines.getSelectionModel().getSelectedItem().toString();
        if(!hLines.contains(getHelplineByName(selected))){
            hLines.add(getHelplineByName(selected));
        }
    }
    
    public void removeSearchHelpline(){
        
    }

    public void saveApproachPolice(Event evnt) {
        String approach = tfApproachPolice.getText();
        String helpline = "Politie";
        int reportID = selectedReport.getReportID();
        boolean succes = saveApproach(approach, helpline, reportID);
        approachSaved(succes);
    }

    public void saveApproachFirefighters(Event evnt) {
        String approach = tfApproachFirefighters.getText();
        String helpline = "Brandweer";
        int reportID = selectedReport.getReportID();
        boolean succes = saveApproach(approach, helpline, reportID);
        approachSaved(succes);
    }

    public void saveApproachAmbulance(Event evnt) {
        String approach = tfApproachAmbulance.getText();
        String helpline = "Ambulance";
        int reportID = selectedReport.getReportID();
        boolean succes = saveApproach(approach, helpline, reportID);
        approachSaved(succes);
    }

    public void approachSaved(boolean succes) {
        Alert alert;
        String message;
        if (succes) {
            alert = new Alert(AlertType.CONFIRMATION);
            message = "Approach saved";
        } else {
            alert = new Alert(AlertType.ERROR);
            message = "Approach not saved";
        }

        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void getAllVehicles() {

    }

    public void informationAccident() {
        if (selectedReport != null) {
            if(cc != null)
            {
                chat.stop();
                //cc.deleteObserver(this);
                cc = null;
                chat = null;
                System.gc();
            }
            taChat.setText("");
            cc = new ChatClient(selectedReport.getReportID());
            chat = new Thread(cc);
            chat.setDaemon(true);

            chat.start();
            cc.addObserver(this);
        }
        System.out.println(selectedReport.toString());
        tpTabs.getSelectionModel().select(tptInfo);
        lblReportDate.setText(selectedReport.getStartDate().toString());
        lblReportDesc.setText(selectedReport.getDescription());
        lblReportExtra.setText(selectedReport.getExtraInformation());
        lblReportLoc.setText(selectedReport.getLocationGPS());
        lblReportWeather.setText(selectedReport.getWeather());
        getApproach();
        fillHelplineLv();
        
        fillAssignedEmployees();
        fillAssignedVehicles();
    }

    public void getApproach() {
        int reportID = selectedReport.getReportID();
        for (Helpline h : getHelplines()) {
            String approach = getApproachReport(reportID, h.getID());
            if (approach == null) {
                approach = "NVT";
            }
            System.out.println(approach);
            if (h.getName().equals("Politie")) {
                tfApproachPolice.setText(approach);
            }
            else if (h.getName().equals("Brandweer")) {
                tfApproachFirefighters.setText(approach);
            }
            else if (h.getName().equals("Ambulance")) {
                tfApproachAmbulance.setText(approach);
            }
        }
    }

    public void btnSendChatClick(Event e) throws NoSuchAlgorithmException {
        cc.setText("Centrale: " + tfChatMessage.getText());
        tfChatMessage.setText("");
    }

    public void makeCollums() {
        tvIncidents.getColumns().clear();

        List<Field> fields = getCollumsReport();
        for (Field f : fields) {
            if (!f.getName().equals("dbm")) {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                if (f.getType().equals(ArrayList.class)) {
                    tc.setCellValueFactory(new PropertyValueFactory<ArrayList<Helpline>, String>(f.getName()));
                } else {
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

    public void makeEmployeeColumns() {
        tvEmpAllAmbulance.getColumns().clear();
        tvEmpAllFire.getColumns().clear();
        tvEmpAllPolice.getColumns().clear();
        
        tvEmpAssAmbulance.getColumns().clear();
        tvEmpAssFire.getColumns().clear();
        tvEmpAssPolice.getColumns().clear();

        List<Field> fieldsEmployees = getColumnsEmployee();
        for (Field f : fieldsEmployees) {
            if (!f.getName().equals("helpline")) {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAllAmbulance.getColumns().add(tc);
            }
        }
        
        for (Field f : fieldsEmployees) {
            if (!f.getName().equals("helpline")) {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAssAmbulance.getColumns().add(tc);
            }
        }

        for (Field f : fieldsEmployees) {
            if (!f.getName().equals("helpline")) {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAllFire.getColumns().add(tc);
            }
        }
        
        for (Field f : fieldsEmployees) {
            if (!f.getName().equals("helpline")) {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAssFire.getColumns().add(tc);
            }
        }

        for (Field f : fieldsEmployees) {
            if (!f.getName().equals("helpline")) {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAllPolice.getColumns().add(tc);
            }
        }
        
        for (Field f : fieldsEmployees) {
            if (!f.getName().equals("helpline")) {
                TableColumn tc = new TableColumn();
                tc.setText(f.getName());
                tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
                tc.setResizable(true);
                tc.setMinWidth(135);
                tvEmpAssPolice.getColumns().add(tc);
            }
        }

        tvEmpAllAmbulance.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvEmpAllAmbulance.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getName());
                selectedEmployee = tvEmpAllAmbulance.getSelectionModel().getSelectedItem();
            }
        });

        tvEmpAllFire.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvEmpAllFire.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getName());
                selectedEmployee = tvEmpAllFire.getSelectionModel().getSelectedItem();
            }
        });

        tvEmpAllPolice.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvEmpAllPolice.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getName());
                selectedEmployee = tvEmpAllPolice.getSelectionModel().getSelectedItem();
            }
        });
        
        //Assigned
        
        tvEmpAssAmbulance.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvEmpAssAmbulance.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getName());
                selectedEmployee = tvEmpAssAmbulance.getSelectionModel().getSelectedItem();
            }
        });

        tvEmpAssFire.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvEmpAssFire.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getName());
                selectedEmployee = tvEmpAssFire.getSelectionModel().getSelectedItem();
            }
        });

        tvEmpAssPolice.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvEmpAssPolice.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getName());
                selectedEmployee = tvEmpAssPolice.getSelectionModel().getSelectedItem();
            }
        });
    }

    public void makeVehicleColumns() {
        tvVehAllAmbulance.getColumns().clear();
        tvVehAllFire.getColumns().clear();
        tvVehAllPolice.getColumns().clear();
        
        tvVehAssAmbulance.getColumns().clear();
        tvVehAssFire.getColumns().clear();
        tvVehAssPolice.getColumns().clear();

        List<Field> fieldsVehicles = getColumnsVehicle();
        for (Field f : fieldsVehicles) {
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAllAmbulance.getColumns().add(tc);
        }
        
        for (Field f : fieldsVehicles) {
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAssAmbulance.getColumns().add(tc);
        }

        for (Field f : fieldsVehicles) {
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAllFire.getColumns().add(tc);
        }
        
        for (Field f : fieldsVehicles) {
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAssFire.getColumns().add(tc);
        }

        for (Field f : fieldsVehicles) {
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAllPolice.getColumns().add(tc);
        }
        
        for (Field f : fieldsVehicles) {
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAssPolice.getColumns().add(tc);
        }

        tvVehAllAmbulance.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAllAmbulance.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Vehicle: " + newValue.getType());
                selectedVehicle = tvVehAllAmbulance.getSelectionModel().getSelectedItem();
            }
        });

        tvVehAllFire.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAllFire.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Vehicle: " + newValue.getType());
                selectedVehicle = tvVehAllFire.getSelectionModel().getSelectedItem();
            }
        });

        tvVehAllPolice.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAllPolice.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Vehicle: " + newValue.getType());
                selectedVehicle = tvVehAllPolice.getSelectionModel().getSelectedItem();
            }
        });
        
        //Assigned
        
        tvVehAssAmbulance.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAssAmbulance.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Vehicle: " + newValue.getType());
                selectedVehicle = tvVehAssAmbulance.getSelectionModel().getSelectedItem();
            }
        });

        tvVehAssFire.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAssFire.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Vehicle: " + newValue.getType());
                selectedVehicle = tvVehAssFire.getSelectionModel().getSelectedItem();
            }
        });

        tvVehAssPolice.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAssPolice.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Vehicle: " + newValue.getType());
                selectedVehicle = tvVehAssPolice.getSelectionModel().getSelectedItem();
            }
        });
    }

    public void fillColums() {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        reports = fillIncidents();
        //reports.sort(cmprtr);
        tvIncidents.setItems(reports);
    }

    public void fillEmployeeColums() {
        ObservableList<Employee> employeesPolice = fillEmployees("Politie");
        tvEmpAllPolice.setItems(employeesPolice);

        ObservableList<Employee> employeesFire = fillEmployees("Brandweer");//FXCollections.observableArrayList();
        tvEmpAllFire.setItems(employeesFire);

        ObservableList<Employee> employeesAmbulance = fillEmployees("Ambulance");
        tvEmpAllAmbulance.setItems(employeesAmbulance);
    }

    public void fillVehicleColumns() {
        ObservableList<Vehicle> vehiclesPolice = fillVehicles("Politie");
        tvVehAllPolice.setItems(vehiclesPolice);

        ObservableList<Vehicle> vehiclesFire = fillVehicles("Brandweer");
        tvVehAllFire.setItems(vehiclesFire);

        ObservableList<Vehicle> vehiclesAmbulance = fillVehicles("Ambulance");
        tvVehAllAmbulance.setItems(vehiclesAmbulance);
    }
    
    public void fillAssignedEmployees(){
        
        ObservableList<Employee> emp = selectedReport.getEmployeesByHelpline(1);
        tvEmpAssPolice.setItems(emp);
        
        emp = selectedReport.getEmployeesByHelpline(2);
        tvEmpAssAmbulance.setItems(emp);
        
        emp = selectedReport.getEmployeesByHelpline(3);
        tvEmpAssFire.setItems(emp);
        
    }
    
    public void fillAssignedVehicles(){
        
        List<Vehicle> veh;
        
        if(tvEmpAssPolice.getItems() != null)
        {
            veh = new ArrayList();
            for(Employee e : tvEmpAssPolice.getItems()){
                if(e.getAssignedVehicle() != null)
                    veh.add(e.getAssignedVehicle());
            }
            tvVehAssPolice.setItems(FXCollections.observableArrayList(veh));
            System.out.println("Added " + veh.size() + " to Police");
        }
        
        if(tvEmpAssAmbulance.getItems() != null)
        {
            veh = new ArrayList();
            
            for(Employee e : tvEmpAssAmbulance.getItems()){
                if(e.getAssignedVehicle() != null)
                    veh.add(e.getAssignedVehicle());
            }
            tvVehAssAmbulance.setItems(FXCollections.observableArrayList(veh));
            System.out.println("Added " + veh.size() + " to Ambulance");
        }
        
        if(tvEmpAssFire.getItems() != null)
        {
            veh = new ArrayList();
            
            for(Employee e : tvEmpAssFire.getItems()){
                if(e.getAssignedVehicle() != null)
                    veh.add(e.getAssignedVehicle());
            }
            tvVehAssFire.setItems(FXCollections.observableArrayList(veh));
            System.out.println("Added " + veh.size() + " to Fire");
        }
    }

    public void closeReport() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close report");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close the report: '" + selectedReport.getTitle() + "'?");
        alert.showAndWait().ifPresent(response -> {;
            if (response == ButtonType.OK) {
                setReportClosed();
            } else {
                System.out.println("doing nothing");
            }
        });
    }

    public void setReportClosed() {
        System.out.println("OK, closing report");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(dateFormat.format(new Date()), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        boolean succes = closeReport(selectedReport.getReportID(), date);
        if (succes) {
            selectedReport.setEndDate(date);
        }
        showReportClosed(succes);
    }

    public void showReportClosed(boolean succes) {
        Alert alert;
        String message;
        if (succes) {
            alert = new Alert(AlertType.CONFIRMATION);
            message = "Report Closed";
            fillColums();
        } else {
            alert = new Alert(AlertType.ERROR);
            message = "Could not close report";
        }

        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public void AddHelpline() {
        cbItem cb = (cbItem) cbHelplines.getValue();
        for (cbItem item : listItems) {
            if (item.getID() == cb.getID()) {
                return;
            }
        }
        selectedReport.updateHelplines(cb.getID());
        listItems.add(cb);
    }
    
    private void fillHelplineLv()
    {
        for(Helpline h : selectedReport.getHelplines())
        {
           cbItem help =  new cbItem((h.getID()), h.getName());
           listItems.add(help);
        }
    }
    
    public void addOrRemoveEmployee(Event e) {
        
        if(selectedEmployee == null && selectedEmployee.getAssignedTo() == null)
            return;
        
        boolean add = true;
        int helplineID = 0;
        TableView tv = null;
        TableView tvVeh = null;
        
        if(e.getSource() == btnAssignEmpPol)
        {
            tv = tvEmpAssPolice;
            helplineID = 1;
        }
        
        else if(e.getSource() == btnRemoveEmpPol){
            tv = tvEmpAssPolice;
            tvVeh = tvVehAssPolice;
            add = false;
            helplineID = 1;
        }
        
        else if(e.getSource() == btnAssignEmpAmbu){
            tv = tvEmpAssAmbulance;
            helplineID = 2;
        }
        
        else if(e.getSource() == btnRemoveEmpAmbu){
            tv = tvEmpAssAmbulance;
            tvVeh = tvVehAssAmbulance;
            add = false;
            helplineID = 2;
        }
        
        else if(e.getSource() == btnAssignEmpFire){
            tv = tvEmpAssFire;
            helplineID = 3;
        }
        
        else if(e.getSource() == btnRemoveEmpFire){
            tv = tvEmpAssFire;
            tvVeh = tvVehAssFire;
            add = false;
            helplineID = 3;
        }
        
        if(helplineID != 0){
            
            Helpline h = getHelplineByID(helplineID);
            
            if(h == null || tv == null)
                return;
            
            if(add){
                if(selectedEmployee.getAssignedTo() == null){
                    selectedEmployee.setAssignedTo(selectedReport);
                    selectedReport.addEmployee(selectedEmployee);
                    tv.getItems().add(selectedEmployee);
                }
            }
            
            else{
                
                if(selectedEmployee.getAssignedTo() == selectedReport){
                    selectedEmployee.setAssignedTo(null);
                    selectedReport.removeEmployee(selectedEmployee);
                    if(selectedEmployee.getAssignedVehicle() != null && tvVeh != null){
                        tvVeh.getItems().remove(selectedEmployee.getAssignedVehicle());
                    }
                    tv.getItems().remove(selectedEmployee);
                }
            }
        }
    }
    
    public void addOrRemoveVehicle(Event e) {
        
        if(selectedVehicle == null || selectedVehicle.assignedEmployee == null)
            return;
        
        boolean add = true;
        int helplineID = 0;
        TableView tv = null;
        
        if(e.getSource() == btnAssignPolVehicle){
            tv = tvVehAssPolice;
            helplineID = 1;
        }
        
        else if(e.getSource() == btnRemovePolVehicle){
            tv = tvVehAssPolice;
            add = false;
            helplineID = 1;
        }
        
        else if(e.getSource() == btnAssignAmbuVehicle){
            tv = tvVehAssAmbulance;
            helplineID = 2;
        }
        
        else if(e.getSource() == btnRemoveAmbuVehicle){
            tv = tvVehAssAmbulance;
            add = false;
            helplineID = 2;
        }
        
        else if(e.getSource() == btnAssignFireVehicle){
            tv = tvVehAssFire;
            helplineID = 3;
        }
        
        else if(e.getSource() == btnRemoveFireVehicle){
            tv = tvVehAssFire;
            add = false;
            helplineID = 3;
        }
        
        if(helplineID != 0){
            
            Helpline h = getHelplineByID(helplineID);
            
            if(h == null || tv == null)
                return;
            
            if(add){
                selectedEmployee.setAssignedVehicle(selectedVehicle);
                selectedVehicle.setAssignedEmployee(selectedEmployee);
                tv.getItems().add(selectedVehicle);
            }
            
            else{
                if(selectedVehicle.getAssignedEmployee() != null){
                    selectedVehicle.setAssignedEmployee(null);
                    selectedEmployee.setAssignedVehicle(null);
                    tv.getItems().remove(selectedVehicle);
                }
            }
        }
    }
    
    public void cgSearchFunction(Event e){
        
        System.out.println("Starting search...");
        
        ObservableList<Report> found = FXCollections.observableArrayList();
        
        for(Helpline h : getHelplines()){
            for(Report r : h.getReports()){
                found.add(r);
            }
        }
        
        tvIncidents.setItems(found);
        
        if(!cgSearchName.getText().isEmpty()){
            
            String query = cgSearchName.getText().toLowerCase();
            
            for(Object r : found.toArray()){
                Report rep = (Report)r;
                if(!rep.getTitle().toLowerCase().contains(query) && !rep.getDescription().toLowerCase().contains(query))
                    found.remove(rep);
            }
            
            cgSearchName.setText("");
        }
        
        if(cgSearchDateStart.getValue() != null){
            
            LocalDate date = cgSearchDateStart.getValue();
            
            for(Object r : found.toArray()){
                Report rep = (Report)r;
                if(rep.getStartDate().toLocalDate().isBefore(date))
                    found.remove(rep);
            }
        }
        
        if(cgSearchDateEnd.getValue() != null){
            
            LocalDate date = cgSearchDateEnd.getValue();
            
            for(Object r : found.toArray()){
                Report rep = (Report)r;
                if(rep.getEndDate().toLocalDate().isAfter(date))
                    found.remove(rep);
            }
        }
    }
}
