/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cims.Employee;
import cims.Helpline;
import i18n.localeSettings;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class LoginOnTheRoadController implements Initializable {

    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void btnLoginClick(Event e) {
        Employee emp = Employee.getEmployeeByInlog(txtName.getText(), txtPassword.getText());

        //Employee emp = new Employee(1, "Harry", new Helpline(1, "Politie"));
        if (emp == null) {
            return;
        }

        try {
            Parent root = null;
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(getClass().getResource("/gui/OnTheRoad.fxml"));
            loader.setResources(localeSettings.getResourceBundle());
            
            loader.load();
            root = loader.getRoot();
            
            
            OnTheRoadFXController controller = loader.<OnTheRoadFXController>getController();
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            
            stage.show();
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    System.out.println("YAY");
                    controller.setEmployee(emp);
                    controller.InitializeChat();
                    controller.updateLabels();
                }
            });           

        } catch (IOException ex) {
            Logger.getLogger(LoginOnTheRoadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
