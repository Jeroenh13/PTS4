package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
/**
 * FXML CONTROLLER CLASS
 */
public class ControlRoomController implements Initializable {

    @FXML Button btnAddEmergency;
    @FXML Button btnRemoveEmergency;
    @FXML Button btnSave;
    @FXML TextArea taDescription;
    @FXML ComboBox cbEmergency;
    @FXML ListView lvEmergency;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
}
