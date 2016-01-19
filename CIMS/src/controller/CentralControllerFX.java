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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    
    
    private final ObservableList<cbItem> listItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeCollums();
        fillColums();
        makeEmployeeColumns();
        fillEmployeeColums();
        makeVehicleColumns();
        fillVehicleColumns();

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
            if (h.getName().equals("Brandweer")) {
                tfApproachFirefighters.setText(approach);
            }
            if (h.getName().equals("Ambulance")) {
                tfApproachAmbulance.setText(approach);
            }
        }
    }

    public void btnSendChatClick(Event e) {
        cc.setText("Centrale: " + tfChatMessage.getText());
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
                tvEmpAllPolice.getColumns().add(tc);
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
    }

    public void makeVehicleColumns() {
        tvVehAllAmbulance.getColumns().clear();
        tvVehAllFire.getColumns().clear();
        tvVehAllPolice.getColumns().clear();

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
            tvVehAllFire.getColumns().add(tc);
        }

        for (Field f : fieldsVehicles) {
            TableColumn tc = new TableColumn();
            tc.setText(f.getName());
            tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
            tc.setResizable(true);
            tc.setMinWidth(135);
            tvVehAllPolice.getColumns().add(tc);
        }

        tvVehAllAmbulance.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAllAmbulance.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getType());
                selectedVehicle = tvVehAllAmbulance.getSelectionModel().getSelectedItem();
            }
        });

        tvVehAllFire.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAllFire.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getType());
                selectedVehicle = tvVehAllFire.getSelectionModel().getSelectedItem();
            }
        });

        tvVehAllPolice.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            if (tvVehAllPolice.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Selected Employee: " + newValue.getType());
                selectedVehicle = tvVehAllPolice.getSelectionModel().getSelectedItem();
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
    
}
