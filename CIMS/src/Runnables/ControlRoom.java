/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Runnables;

import i18n.localeSettings;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Bas
 */
public class ControlRoom extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/ControlRoom.fxml"),localeSettings.getResourceBundle());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            /*
             Pane controlRoom = (Pane)FXMLLoader.load(CIMS.class.getResource("/gui/ControlRoom.fxml"));
             Scene scene = new Scene(controlRoom);
             primaryStage.setScene(scene);
             primaryStage.setTitle("Control Room");
             primaryStage.show();*/
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
