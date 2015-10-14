package controller;

import cims.Helpline;
import cims.Report;
import cims.cbItem;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML CONTROLLER CLASS
 */
public class ControlRoomController implements Initializable {

    @FXML
    private Button btnAddEmergency;
    @FXML
    private Button btnRemoveEmergency;
    @FXML
    private Button btnSave;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField txtTitle;
    @FXML
    private ComboBox cbEmergency;
    @FXML
    private ListView<cbItem> lvEmergency;

    private Report report;
    final ObservableList<cbItem> listItems = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lvEmergency.setItems(listItems);
        Helpline helpline = new Helpline();
        for (Helpline help : helpline.getLines()) {
            cbEmergency.getItems().add(new cbItem(help.getID(), help.getName()));
        }
    }

    public void btnRemoveClick(Event evnt) {
        cbItem item = lvEmergency.getSelectionModel().getSelectedItem();
        listItems.remove(item);
    }

    public void btnSaveClick(Event evnt) {
        ArrayList lines = new ArrayList();
        for (cbItem item : listItems) {
            lines.add(new Helpline(item.getID(), item.toString()));        
        }
        report = new Report(0, taDescription.getText(), null, null, null,lines,txtTitle.getText() );
        if (report.saveReport()) {
            JOptionPane.showMessageDialog(null, "Succesvol toegevoegt", "Succes", 1);
            listItems.clear();
            taDescription.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Er is iets verkeerd gegaan", "Failed", 0);
        }
    }

    public void btnAddClick(Event evnt) {
        cbItem cb = (cbItem) cbEmergency.getValue();
        for (cbItem item : listItems) {
            if (item.getID() == cb.getID()) {
                return;
            }
        }
        listItems.add(cb);
    }
}
