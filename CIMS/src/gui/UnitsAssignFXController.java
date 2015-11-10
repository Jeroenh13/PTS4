/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import cims.Employee;
import i18n.localeSettings;
import java.io.IOException;
import javafx.fxml.FXML;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    @FXML TableColumn tcBatchNrAss;
    @FXML TableColumn tcPersonAss;
    @FXML TableColumn tcAvailableAss;
    @FXML TableColumn tcFunctionAss;
    @FXML TableColumn tcDepartmentAss;
    @FXML TableColumn tcRegionAss;
    @FXML TableColumn tcCommuneAss;
    @FXML TableColumn tcIncidentAss;
    @FXML TableColumn tcNiveauAss;
    @FXML TableColumn tcTeamAss;
    @FXML TableColumn tcFromDateAss;
    @FXML TableColumn tcTillDateAss;
    @FXML GridPane gdpComboAss;
    
    // overview
    @FXML TextField tfName;
    @FXML TextField tfBadgeNr;
    @FXML TextField tfIncident;
    @FXML Button btnSearch;
    @FXML DatePicker dtpFromDate;
    @FXML DatePicker dtpTillDate;
    @FXML TableView<Employee> tvOverview;
    @FXML TableColumn tcBatchNr;
    @FXML TableColumn tcPerson;
    @FXML TableColumn tcAvailable;
    @FXML TableColumn tcFunction;
    @FXML TableColumn tcDepartment;
    @FXML TableColumn tcRegion;
    @FXML TableColumn tcCommune;
    @FXML TableColumn tcIncident;
    @FXML TableColumn tcNiveau;
    @FXML TableColumn tcTeam;
    @FXML TableColumn tcFromDate;
    @FXML TableColumn tcTillDate;
    @FXML GridPane gdpCombo;

    HashMap<ComboBox, String> comboBoxes;
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
        setListviewIncidents();
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
    
    private void setListviewIncidents(){
        getIncidents();
        lvIncident.setItems(getHelpline().getReports());
    }
}
