/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import cims.Employee;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Anna-May
 */
public class UnitsAssignFXController extends controller.UnitsAssignControler implements Initializable 
{
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

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    // <editor-fold desc="Select comboxes: Overview & Assign">
    public void selectAvailable(Event evt) {
        
    }
    
    public void selectFunction(Event evt) {
        
    }
    
    public void selectRegion(Event evt) {
        
    }
    
    public void selectDepartment(Event evt) {
        
    }
    
    public void selectCommune(Event evt) {
        
    }
    
    public void selectNiveau(Event evt) {
        
    }
    
    public void selectTeam(Event evt) {
        
    }
    
    public void selectDateFrom(Event evt) {
        
    }
    
    public void selectDateTill(Event evt) {
        
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
}
