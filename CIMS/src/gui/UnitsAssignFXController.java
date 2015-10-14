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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    @FXML TableView tvAssign;
    @FXML TextField tfNameAss;
    @FXML TextField tfBatchNrAss;
    @FXML Button btnNameAss;
    @FXML Button btnBadgeNrAss;
    @FXML ComboBox cbFunctionAss;
    @FXML ComboBox cbAvailableAss;
    @FXML ComboBox cbDepartmentAss;
    @FXML ComboBox cbRegionAss;
    @FXML ComboBox cbCommuneAss;
    @FXML ComboBox cbNiveauAss;
    @FXML ComboBox cbTeamAss;
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
    @FXML TableView<Employee> tbOverview;
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

    HashMap<ComboBox, String> comboBoxesAss;
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
        makeMapComboBoxes();
        makeMapComboBoxesAss();
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
    
    public void searchName(Event evt) {
        
    }
    
    public void searchBatchNr(Event evt) {
        
    }
    
    public void searchIncident(Event evt) {
        
    }
    // </editor-fold>
    // <editor-fold desc="Set tables: Overview & Assign">
   public void setTable(){
//        ArrayList<Employee> emps = this.getListOfPersons();
//        ObservableList<Employee> e = FXCollections.observableArrayList(emps);
//        tcPerson.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
//        tcFunction.setCellValueFactory(new PropertyValueFactory<Employee, String>("Function"));
//        tcAvailable.setCellValueFactory(new PropertyValueFactory<Employee, String>("Available"));
//        tcDepartment.setCellValueFactory(new PropertyValueFactory<Employee, String>("Department"));
//        tcTown.setCellValueFactory(new PropertyValueFactory<Employee, String>("Town"));
//        tcNiveau.setCellValueFactory(new PropertyValueFactory<Employee, String>("Level"));
//        tcTeam.setCellValueFactory(new PropertyValueFactory<Employee, String>("Team"));
//        tcAppointedTo.setCellValueFactory(new PropertyValueFactory<Employee, String>("AppointedTo"));
//        tbOverview.setItems(e);
        
        ArrayList<Employee> emps = this.getListOfPersons();
        ObservableList<Employee> e = FXCollections.observableArrayList(emps);
        
        tcBatchNr.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcPerson.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcAvailable.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcFunction.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcDepartment.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcRegion.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcCommune.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcNiveau.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcTeam.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcFromDate.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        tcTillDate.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        
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
//            resetSpecifications();
//            setTableAss();
        } else if (tab == tpgOverview) {
//            resetSpecifications();
//            setTable();
        } else if (tab == tpgReport){
            
        }
    }
    
    // TO DO inhoud van databasemanager verkrijgen 
    private void makeMapComboBoxesAss(){
        comboBoxesAss = new HashMap<>();
        int width = 185;
        //gdpComboAss.gridLinesVisibleProperty().set(true); 
        gdpComboAss.getColumnConstraints().clear();
        
        HashMap<String, ObservableList> specifications = getSpecificationsTypes();
        int column, row = -1;
        double dbForCount;
        for (Map.Entry<String, ObservableList> entry : specifications.entrySet()){
            ObservableList items = entry.getValue();
            if(items != null){
                // set comboboxes
                ComboBox cbTypes = new ComboBox();
                cbTypes.setMinWidth(width); 
                cbTypes.setMaxWidth(width);
                String key = entry.getKey();
                comboBoxesAss.put(cbTypes, key);
                
                dbForCount = comboBoxesAss.size();
                dbForCount = dbForCount/4.0;
                column = (int) Math.ceil(dbForCount)*2-1;
                
                row++;
                if(row == 4){
                    row = 0;
                }
                
                //gdpCombo.add(cbTypes,column,row);
                gdpComboAss.add(cbTypes,column,row);
                cbTypes.setItems(items);
                
                // set labels
                Label lbl = new Label();
                lbl.setText(localeSettings.getResourceBundle().getString(key.toLowerCase()));
                
                //gdpCombo.add(lbl, column - 1, row);
                gdpComboAss.add(lbl,column - 1,row);
            }
        }
    }
    
    private void makeMapComboBoxes(){
        comboBoxes = new HashMap<>();
        int width = 185;
        //gdpCombo.gridLinesVisibleProperty().set(true); 
        gdpCombo.getColumnConstraints().clear();
        
        HashMap<String, ObservableList> specifications = getSpecificationsTypes();
        int column, row = -1;
        double dbForCount;
        for (Map.Entry<String, ObservableList> entry : specifications.entrySet()){
            ObservableList items = entry.getValue();
            if(items != null){
                // set comboboxes
                ComboBox cbTypes = new ComboBox();
                cbTypes.setMinWidth(width); 
                cbTypes.setMaxWidth(width);
                String key = entry.getKey();
                comboBoxes.put(cbTypes, key);
                
                dbForCount = comboBoxes.size();
                dbForCount = dbForCount/4.0;
                column = (int) Math.ceil(dbForCount)*2-1;
                
                row++;
                if(row == 4){
                    row = 0;
                }
                
                gdpCombo.add(cbTypes,column,row);
                cbTypes.setItems(items);
                
                // set labels
                Label lbl = new Label();
                lbl.setText(localeSettings.getResourceBundle().getString(key.toLowerCase()));
                
                gdpCombo.add(lbl,column - 1,row);
            }
        }
    }
}
