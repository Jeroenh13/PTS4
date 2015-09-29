/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.lynden.gmapsfx.*;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Jeroen Hendriks
 */
public class GMapsController extends Application implements MapComponentInitializedListener {

    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    
    @FXML
    private GoogleMapView mapView;   
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void mapInitialized() {
        LatLong center = new LatLong(47.606189, -122.335842);
        mapComponent.addMapReadyListener(() -> {
            // This call will fail unless the map is completely ready.
            checkCenter(center);
        });
        System.out.println("works");
    }
    
    private void checkCenter(LatLong center)
    {
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mapComponent = new GoogleMapView();
        mapComponent.addMapInializedListener(this);
        
        Parent root = FXMLLoader.load(getClass().getResource("GMaps.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
