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
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
    
    // assign (for May)
    @FXML ListView lvIncident;
    @FXML TableView tvAssign;
    @FXML TextField tfNameAss;
    @FXML TextField tfBatchNrAss;
    @FXML Button btnNameAss;
    @FXML Button btnBatchNrAss;
    @FXML ComboBox cbFunctionAss;
    @FXML ComboBox cbAvailableAss;
    @FXML ComboBox cbDepartmentAss;
    @FXML ComboBox cbRegionAss;
    @FXML ComboBox cbCommuneAss;
    @FXML ComboBox cbNiveauAss;
    @FXML ComboBox cbTeamAss;
    
    // overview (for Kitty)
    @FXML TextField tfName;
    @FXML TextField tfBatchNr;
    @FXML TextField tfIncident;
    @FXML Button btnSearchName;
    @FXML Button btnSearchBatchNr;
    @FXML Button btnSearchIncident;
    @FXML ComboBox cbAvailable;
    @FXML ComboBox cbFunction;
    @FXML ComboBox cbRegion;
    @FXML ComboBox cbDepartment;
    @FXML ComboBox cbCommune;
    @FXML DatePicker dtpFromDate;
    @FXML DatePicker dtpTillDate;
    @FXML TableView<Employee> tbOverview = new TableView<Employee>();
    @FXML TableColumn tcName = new TableColumn("Name");
    @FXML TableColumn tcFunction = new TableColumn("Function");
    @FXML TableColumn tcAvailable = new TableColumn("Available");
    @FXML TableColumn tcDepartment = new TableColumn("Department");
    @FXML TableColumn tcTown = new TableColumn("Town");
    @FXML TableColumn tcLevel = new TableColumn("Level");
    @FXML TableColumn tcTeam = new TableColumn("Team");
    @FXML TableColumn tcAppointedTo = new TableColumn("AppointedTo");

    HashMap<ComboBox, String> comboBoxes;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // make map of specifications to chose from on GUI
        makeMapComboBoxes();
        // get search specifications and subspecifications
        getTypeSpecifications();
        // fill comboboxes with subspecifications
        fillComboBoxes();
        // set tabel of assign unnits, fill with persons of helpline
        setTableAss();
    }    
    
    //temp int for testing locale 0 = en, 1 = nl
    int tempLocale = 0;
    public void setLocale(Event evt) {
        if(tempLocale == 0)
        {
            localeSettings.setLocale("nl", "NL");
            tempLocale = 1;
        }
        else{
            localeSettings.setLocale("en", "US");
            tempLocale = 0;
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
        addSpecification(value, (String) combo.getSelectionModel().getSelectedItem());
    }
    
    public void searchName(Event evt) {
        
    }
    
    public void searchBatchNr(Event evt) {
        
    }
    
    public void searchIncident(Event evt) {
        
    }
    // </editor-fold>
    // <editor-fold desc="Set tables: Overview & Assign">
    public void setTable(){
        ArrayList<Employee> emps = this.getListOfPersons();
        ObservableList<Employee> e = FXCollections.observableArrayList(emps);
        tcName.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcFunction.setCellValueFactory(new PropertyValueFactory<Employee, String>("Function"));
        tcAvailable.setCellValueFactory(new PropertyValueFactory<Employee, String>("Available"));
        tcDepartment.setCellValueFactory(new PropertyValueFactory<Employee, String>("Department"));
        tcTown.setCellValueFactory(new PropertyValueFactory<Employee, String>("Town"));
        tcLevel.setCellValueFactory(new PropertyValueFactory<Employee, String>("Level"));
        tcTeam.setCellValueFactory(new PropertyValueFactory<Employee, String>("Team"));
        tcAppointedTo.setCellValueFactory(new PropertyValueFactory<Employee, String>("AppointedTo"));
        tbOverview.setItems(e);
    }
    
    public void setTableAss(){
        
    }
    // </editor-fold>
    
    // <editor-fold desc="Report">
    // TO DO
    // </editor-fold>
    
    // TO DO Set listview of report and assign
    
    
    public void tabSwitch(Event evt){
        Tab tab = (Tab) evt.getSource();
        
        if(tab == tpgAssignUnnits){
            resetSpecifications();
            setTableAss();
        } else if (tab == tpgOverview) {
            resetSpecifications();
            setTable();
        } else if (tab == tpgReport){
            
        }
    }
    
    private void fillComboBoxes(){
        for(Map.Entry<ComboBox, String> entry : comboBoxes.entrySet()){ 
            ComboBox comboB = entry.getKey();
            String value = entry.getValue();
            
            comboB.setItems(getFunctionTypes(value));
        }
    }
    
    // TO DO inhoud van databasemanager verkrijgen 
    private void makeMapComboBoxes(){
        comboBoxes = new HashMap<>();
        comboBoxes.put(cbAvailable, "available");
        comboBoxes.put(cbFunction, "function");
        comboBoxes.put(cbRegion, "region");
        comboBoxes.put(cbDepartment, "department");
        comboBoxes.put(cbCommune, "commune");
        comboBoxes.put(cbFunctionAss, "function");
        comboBoxes.put(cbAvailableAss, "available");
        comboBoxes.put(cbDepartmentAss, "department");
        comboBoxes.put(cbRegionAss, "region");
        comboBoxes.put(cbCommuneAss, "commune");
        comboBoxes.put(cbNiveauAss, "niveau");
        comboBoxes.put(cbTeamAss, "team");
    }
}
