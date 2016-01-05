package controller;

import cims.Helpline;
import cims.Report;
import cims.cbItem;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javax.swing.JOptionPane;

/**
 * FXML CONTROLLER CLASS
 */
public class ControlRoomController implements Initializable, MapComponentInitializedListener {

    protected GoogleMapView mapComponent;
    protected GoogleMap map;

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
    @FXML
    private BorderPane GMapsPane;
    @FXML
    private TextField tfLatitude;
    @FXML
    private TextField tfLongitude;
    @FXML
    private Button checkLatLong;

    private Report report;
    final ObservableList<cbItem> listItems = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapComponent = new GoogleMapView();
        mapComponent.addMapInializedListener(this);

        GMapsPane.setCenter(mapComponent);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lvEmergency.setItems(listItems);
                Helpline helpline = new Helpline();
                helpline.getLines().stream().forEach((help) -> {
                    cbEmergency.getItems().add(new cbItem(help.getID(), help.getName()));
                });
            }
        });
    }

    public void btnRemoveClick(Event evnt) {
        cbItem item = lvEmergency.getSelectionModel().getSelectedItem();
        listItems.remove(item);
    }

    public void btnSaveClick(Event evnt) {
        ArrayList lines = new ArrayList();
        listItems.stream().forEach((item) -> {
            lines.add(new Helpline(item.getID(), item.toString()));
        });
        report = new Report(0, taDescription.getText(), null, "[" + tfLatitude.getText() + "," + tfLongitude.getText() + "]", null, lines, txtTitle.getText());
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

    @Override
    public void mapInitialized() {
        LatLong center = new LatLong(51.45197, 5.48106);
        mapComponent.addMapReadyListener(() -> {
        });

        MapOptions options = new MapOptions();
        options.center(center)
                .mapMarker(true)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(true)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapComponent.createMap(options);
        map.fitBounds(new LatLongBounds(new LatLong(51.45197+ 0.05, 5.48106- 0.05), center));
        map.setCenter(center);
    }

    public void checkLatLong(Event e) {
        if(tfLatitude.getText() != null &&tfLongitude.getText() != null)
        setLatLong(Double.parseDouble(tfLatitude.getText()), Double.parseDouble(tfLongitude.getText()));
    }

    public void setLatLong(double lat, double lng) {
        LatLong center = new LatLong(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        LatLong markerLatLong = new LatLong(lat, lng);     
        
        markerOptions.position(markerLatLong)
                .title("Report")
                .animation(Animation.DROP)
                .visible(true);

        final Marker myMarker = new Marker(markerOptions);

        map.addMarker(myMarker);

        map.fitBounds(new LatLongBounds(new LatLong(lat+ 0.05, lng- 0.05), center));
        
        map.setCenter(center);
    }
}
