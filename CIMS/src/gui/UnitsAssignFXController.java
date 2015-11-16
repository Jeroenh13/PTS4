/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import cims.Employee;
import cims.Report;
import i18n.localeSettings;
import java.io.IOException;
import javafx.fxml.FXML;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
//import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Anna-May
 */
public class UnitsAssignFXController extends controller.UnitsAssignControler implements Initializable 
{
    @FXML Tab tpgAssignUnnits;
    @FXML Tab tpgOverview;
    @FXML Tab tpgReport;
    
    @FXML AnchorPane root;
    
    // assign
    @FXML ListView lvIncident;
    @FXML TableView<Employee> tvAssign;
    @FXML TextField tfNameAss;
    @FXML TextField tfBadgeNrAss;
    @FXML Button btnSearchAss;
    @FXML ListView lvReportEmps;
    @FXML GridPane gdpComboAss;
    @FXML Label lblStartDateAss;
    @FXML Label lblDescriptionAss;
    @FXML Button btnRemovePerson;
    @FXML DatePicker dtpBeginDateAss;
    @FXML DatePicker dtpEndDateAss;
    @FXML Slider sdrEndHour;
    @FXML Slider sdrEndMinute;
    @FXML Label lblEndMinutes;
    @FXML Label lblEndHours;
    @FXML Slider sdrStartHour;
    @FXML Slider sdrStartMinute;
    @FXML Label lblStartMinutes;
    @FXML Label lblStartHours;
    @FXML Button btnSavePersons;
    
    // overview
    @FXML TextField tfName;
    @FXML TextField tfBadgeNr;
    @FXML TextField tfIncident;
    @FXML Button btnSearch;
    @FXML DatePicker dtpFromDate;
    @FXML DatePicker dtpTillDate;
    @FXML TableView<Employee> tvOverview;
    @FXML GridPane gdpCombo;
    @FXML Label lblStartDate;
    @FXML Label lblDescription;

    private HashMap<ComboBox, String> comboBoxes;
    private boolean selectLvEmpsFirst;
    private boolean selectTvEmpsFirst;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // make map of specifications to chose from on GUI
//        makeMapComboBoxes();
//        // get search specifications and subspecifications
//        getTypeSpecifications();
//        // fill comboboxes with subspecifications
//        fillComboBoxes();
//        // set tabel of assign unnits, fill with persons of helpline
//        setTableAss();
        fillSpecificationsTypes();
        makeComboBoxesAndColumns();
        search(true, "", -1, "", null, null);
        search(false, "", -1, "", null, null);
        selectLvEmpsFirst = false;
        selectTvEmpsFirst = false;
        setIncidents(); 
    }    
    
    
    public void setLocale(Event evt) {
        if(localeSettings.tempLocale == 0)
        {
            localeSettings.setLocale("nl", "NL");
            localeSettings.tempLocale = 1;
        }
        else{
            localeSettings.setLocale("en", "US");
            localeSettings.tempLocale = 0;
        }
        
        Scene scene = root.getScene();
        try {
            scene.setRoot(FXMLLoader.load(getClass().getResource("unitsAssign.fxml"),localeSettings.getResourceBundle()));
        } catch (IOException ex) {
            Logger.getLogger(UnitsAssignFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // <editor-fold desc="Select comboxes: Overview & Assign">
    public void select(Event evt) {
        ComboBox combo = (ComboBox) evt.getSource(); 
        String value = comboBoxes.get(combo);
        adjustSpecification(value, (String) combo.getSelectionModel().getSelectedItem());
    }
    
    public void search(Event evt) { 
        int badgeNr;
        if(evt.getSource() == btnSearchAss){
            if(tfBadgeNrAss.getText().equals("")){ 
                badgeNr = -1;
            }else{
                badgeNr = Integer.parseInt(tfBadgeNrAss.getText());
            }
            search(true, tfNameAss.getText(), badgeNr, "", null, null);
            tvAssign.setItems(getHelpline().getEmployeesAss());
        } else if(evt.getSource() == btnSearch){
            if(tfBadgeNr.getText().equals("")){ 
                badgeNr = -1;
            }else{
                badgeNr = Integer.parseInt(tfBadgeNr.getText());
            }
            LocalDate fromDate = dtpFromDate.getValue();
            LocalDate tillDate = dtpTillDate.getValue();
            search(false, tfName.getText(), badgeNr, tfIncident.getText(), fromDate, tillDate);
        }
    }
    // </editor-fold>
    // <editor-fold desc="Set tables: Overview & Assign">
   
    // </editor-fold>
    
    // <editor-fold desc="Report">
    // TO DO
    // </editor-fold>
    
    // TO DO Set listview of report and assign
    
    
    public void tabSwitch(Event evt){
        Tab tab = (Tab) evt.getSource();
        
        if(tab == tpgAssignUnnits){
//            resetSpecifications();
//            setTableAss();
        } else if (tab == tpgOverview) {
//            resetSpecifications();
//            setTable();
        } else if (tab == tpgReport){
            
        }
    }
    
    private void makeComboBoxesAndColumns(){
        comboBoxes = new HashMap<>();
        int width = 185;
        // reset size of columns and rows
        gdpCombo.getColumnConstraints().clear();
        gdpCombo.getRowConstraints().clear();
        gdpComboAss.getColumnConstraints().clear();
        gdpComboAss.getRowConstraints().clear();
        
        tvOverview.getColumns().clear();
        tvAssign.getColumns().clear();
        
        HashMap<String, ObservableList> specifications = getSpecificationsTypes();
        int column, row = -1;
        double dbForCount;
        for (Map.Entry<String, ObservableList> entry : specifications.entrySet()){
            ObservableList items = entry.getValue();
            String key = entry.getKey();
            if(items.get(0).equals("no selection")){ 
                // make comboboxes
                ComboBox cbTypes = new ComboBox();
                ComboBox cbTypesAss = new ComboBox();
                
                // set events on comboboxes
                cbTypes.setOnAction((event) -> {
                    select(event);
                });
                cbTypesAss.setOnAction((event) -> {
                    select(event);
                });
                
                // set size and content comboboxes
                cbTypes.setMinWidth(width); 
                cbTypes.setMaxWidth(width);
                cbTypes.setItems(items);
                cbTypes.setValue("no selection");
                cbTypesAss.setMinWidth(width); 
                cbTypesAss.setMaxWidth(width);
                cbTypesAss.setItems(items);
                cbTypesAss.setValue("no selection");
                // add comboboxes to HashMap
                comboBoxes.put(cbTypes, key);
                comboBoxes.put(cbTypesAss, key);
                
                // calculate positions for the grids 
                // --> column
                dbForCount = comboBoxes.size()/2;
                dbForCount = dbForCount/4.0;
                column = (int) Math.ceil(dbForCount)*2-1;
                // --> row
                row++;
                if(row == 4){
                    row = 0;
                }
                
                // add comboboxes to grids
                gdpCombo.add(cbTypes,column,row);
                gdpComboAss.add(cbTypesAss,column,row);
                
                // make labels
                Label lbl = new Label();
                Label lblAss = new Label();
                // set size and text labels
                lbl.setMinWidth(width/2); 
                lbl.setMaxWidth(width/2);
                //lbl.setText(localeSettings.getResourceBundle().getString(key.toLowerCase()));
                lbl.setText(key);
                lblAss.setMinWidth(width/2); 
                lblAss.setMaxWidth(width/2);
                lblAss.setText(key);
                //lblAss.setText(localeSettings.getResourceBundle().getString(key.toLowerCase()));
                
                // add labels to the grids
                gdpCombo.add(lbl,column - 1,row);
                gdpComboAss.add(lblAss,column - 1,row);
                setTables(key);
            }else if(items.get(0).equals("table")){
                setTables(key);
            }    
        }
        
        tvOverview.setItems(getHelpline().getEmployees());
        tvAssign.setItems(getHelpline().getEmployeesAss());
        //tvAssign.setColumnResizePolicy();
        
        //tvAssign.setColumnResizePolicy((TableView.ResizeFeatures param) -> true);
        
    }
    
    private void setTables(String key){
        TableColumn tc = new TableColumn();
        TableColumn tcAss = new TableColumn();
        tc.setText(key);
        tcAss.setText(key);
        
        tc.setCellValueFactory(
            new PropertyValueFactory<>(key)
        );
        tcAss.setCellValueFactory(
            new PropertyValueFactory<>(key) //Employee,String
        );
        
        tc.setResizable(true); 
        tcAss.setResizable(true);
        int widthh = 135;
        tc.setMaxWidth(widthh);
        tc.setMinWidth(widthh);
        tcAss.setMaxWidth(widthh);
        tcAss.setMinWidth(widthh);

        tvOverview.getColumns().add(tc);
        tvAssign.getColumns().add(tcAss);
    }
    
    private void setIncidents(){
        getIncidents();
        lvIncident.setItems(getHelpline().getReports());
        
        lvIncident.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            setSelectedReport((Report) newValue);
            //setSelectedReport(Report report)
            lvReportEmps.setItems(this.getEmployeesForReport());
            lblDescriptionAss.setText("Omschrijving:\n" + getSelectedReport().getDescription());
            lblStartDateAss.setText("Startdatum en tijd: " + getSelectedReport().getStartDate().toString()); 
        });
        
        lvReportEmps.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // if there was a value selected before clicking on new value, save the changes
            Employee oldEmp = (Employee) oldValue;
            Employee newEmp = (Employee) newValue;
            
            if(oldEmp != null && newEmp != null){
                if(oldEmp.getBadgeNR() == newEmp.getBadgeNR()){
                    return;
                }else{
                    selectLvEmpsFirst = true;
                }
            }else if(oldEmp == null && newEmp == null){
                return;
            }else if(selectTvEmpsFirst != true){
                selectLvEmpsFirst = true;
            }
            
            if(oldEmp != null){
                if(this.getEmployeesForReport().contains(oldEmp)){ 
                    LocalDateTime start = oldEmp.getStart();
                    LocalDateTime end = oldEmp.getEnd();

                    if(dtpBeginDateAss.getValue() != null){
                        LocalTime startTime = LocalTime.of((int)sdrStartHour.getValue(),(int)sdrStartMinute.getValue());
                        start = LocalDateTime.of(dtpBeginDateAss.getValue(), startTime);
                    }

                    if(dtpEndDateAss.getValue() != null){
                        LocalTime endTime = LocalTime.of((int)sdrEndHour.getValue(),(int)sdrEndMinute.getValue());
                        end = LocalDateTime.of(dtpEndDateAss.getValue(), endTime);
                    }

                    this.adjustDate(start, end, oldEmp);
                }
            }

            if (newEmp != null) {
                if(newEmp.getStart() != null){
                    dtpBeginDateAss.setValue(newEmp.getStart().toLocalDate()); 
                    sdrStartHour.setValue(newEmp.getStart().getHour());
                    sdrStartMinute.setValue(newEmp.getStart().getMinute()); 
                }else{
                    dtpBeginDateAss.setValue(null);
                    sdrStartHour.setValue(0);
                    sdrStartMinute.setValue(0);
                }
                if(newEmp.getEnd() != null){
                    dtpEndDateAss.setValue(newEmp.getEnd().toLocalDate());
                    sdrEndHour.setValue(newEmp.getEnd().getHour());
                    sdrEndMinute.setValue(newEmp.getEnd().getMinute()); 
                }else{
                    dtpEndDateAss.setValue(null);
                    sdrEndHour.setValue(0);
                    sdrEndMinute.setValue(0);
                }

                if(this.getSelectedReport().getEmployees().contains(newEmp)){ 
                    btnRemovePerson.setDisable(true);
                }else{
                    btnRemovePerson.setDisable(false);
                }
                
                setSelectedEmployee(newEmp);
                if(selectTvEmpsFirst == false){
                    tvAssign.getSelectionModel().select(newEmp);
                }
            }else{
                dtpBeginDateAss.setValue(null);
                dtpEndDateAss.setValue(null);
                sdrStartHour.setValue(0);
                sdrStartMinute.setValue(0);
                sdrEndHour.setValue(0);
                sdrEndMinute.setValue(0);
            }  
            selectLvEmpsFirst = false;
        });
        
        tvAssign.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(selectLvEmpsFirst == true){
                selectLvEmpsFirst = false;
                return;
            }
            
            if(newValue != null && oldValue != null){
                if(oldValue.getBadgeNR() == newValue.getBadgeNR()){
                    return;
                }else{
                    selectTvEmpsFirst = true;
                }
            }else if(oldValue == null && newValue == null){
                return;
            }else{
                selectTvEmpsFirst = true;
            }
            
            if (newValue != null) { 
                if(!getEmployeesForReport().contains(newValue)){
                    lvReportEmps.getSelectionModel().clearSelection();
                }else {
                    lvReportEmps.getSelectionModel().select(newValue);  // Platform.runLater(() -> {  });
                }
                setSelectedEmployee(newValue);
            }
            
            selectTvEmpsFirst = false;
        });
        
        DecimalFormat decimalFormat = new DecimalFormat("00");
        
        lblStartMinutes.setText(decimalFormat.format(sdrStartMinute.getValue()));
        lblStartHours.setText(decimalFormat.format(sdrStartHour.getValue()));
        
        sdrStartMinute.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            lblStartMinutes.setText(decimalFormat.format(new_val));
        });
        
        sdrStartHour.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            lblStartHours.setText(decimalFormat.format(new_val));
        });
        
        lblEndMinutes.setText(decimalFormat.format(sdrEndMinute.getValue()));
        lblEndHours.setText(decimalFormat.format(sdrEndHour.getValue()));
        
        sdrEndMinute.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            lblEndMinutes.setText(decimalFormat.format(new_val));
        });
        
        sdrEndHour.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            lblEndHours.setText(decimalFormat.format(new_val));
        });
    }
    
    public void addEmployeeToReport(Event evt){
        if(getSelectedEmployee() != null){
            if(getSelectedEmployee().getAssignedTo() == null){
                if(!this.addEmployee()){
                    giveAlartInformation("Medewerker al toegekend aan een incident.");
                }
                else{
                    this.lvReportEmps.getSelectionModel().select(getSelectedEmployee());
                }
            }else{
                giveAlartInformation("Medewerker al toegekend aan een incident.");
            }
        }else{
            giveAlartInformation("No employee selected.");
        }
    }
    
    private void giveAlartInformation(String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    
    public void removeEmployeeToReport(Event evt){
        if(this.lvReportEmps.getSelectionModel().getSelectedIndex() != -1){
            removeEmployee();
        }
    }
    
    public void saveEmployeeToReport(Event evt){
        saveEmpForReport();
    }
}
