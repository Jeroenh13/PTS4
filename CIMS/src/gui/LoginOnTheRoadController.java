/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import cims.Employee;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class LoginOnTheRoadController implements Initializable {

    @FXML private PasswordField txtPassword;
    @FXML private TextField txtName;
    @FXML private Button btnLogin;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void btnLoginClick(Event e)
    {
        Employee e = Employee.getEmployeeByInlog(txtName.getText(),txtPassword.getText());
    }
    
    
}
