/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import ChatServer.ChatClient;
import Database.DatabaseManager;
import cims.Employee;
import cims.Helpline;
import cims.Report;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;

/**
 *
 * @author Jeroen Hendriks
 */
public class OnTheRoadFXController implements Initializable, Observer {

    private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
    private static String arg1;
    @FXML
    private MediaView mvTest;
    @FXML
    private TextField tfSendChat;
    @FXML
    private TextArea taChat;
    @FXML
    private Button btnSendChat;

    // information incident
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblStartIncident;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblGPS;
    @FXML
    private Label lblExtraInfoLoc;
    @FXML
    private Label lblApproach;

    // information login & partner
    @FXML
    private Label lblLogInName;
    @FXML
    private Label lblLogInFunction;
    @FXML
    private Label lblLogInStart;
    @FXML
    private Label lblPartnerName;
    @FXML
    private Label lblPartnerFunction;
    @FXML
    private Label lblPartnerStart;

    // information weather
    @FXML
    private TabPane tpImageVideo;
    // video
    @FXML
    private Tab tpgVideo;
    @FXML
    private TableView tvVideo;
    @FXML
    private TableColumn tcVideo;
    @FXML
    private Button btnRemoveVideo;
    @FXML
    private TextField tfVideo;
    @FXML
    private Button btnBrowseVideo;
    @FXML
    private Button btnAddVideo;
    // image
    @FXML
    private Tab tpgImage;
    @FXML
    private TableView tvImages;
    @FXML
    private TableColumn tcImage;
    @FXML
    private Button btnRemoveImage;
    @FXML
    private TextField tfImage;
    @FXML
    private Button btnBrowseImage;
    @FXML
    private Button btnAddImage;
    // play
    @FXML
    private Tab tpgPlay;
    @FXML
    private Button btnBack;

    Employee emp = null;

    ChatClient cc = null;

    Thread chat;

    private List<Helpline> helplines = new ArrayList<>();
    private final DatabaseManager dbm = new DatabaseManager();
    private ObservableList<Report> reports = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = new Media((arg1 != null) ? arg1 : MEDIA_URL);
        MediaPlayer mp = new MediaPlayer(media);
        //mp.setAutoPlay(true);
        mvTest.mediaPlayerProperty().set(mp);
    }

    public void InitializeChat() {
        if (emp != null) {
            cc = new ChatClient(emp.getAssignedTo().getReportID());
            chat = new Thread(cc);
            chat.setDaemon(true);

            chat.start();
            cc.addObserver(this);
        } else {
            System.out.println("No employee found");
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        taChat.setText(taChat.getText() + "\n" + ((ChatClient) o).getText());
    }

    public void btnSendChatClick(Event e) throws NoSuchAlgorithmException {
        cc.setText("On the Road: " + tfSendChat.getText());
        taChat.setText("");
    }

    public void setEmployee(Employee emp) {
        setActiveEmployee(emp.getBadgeNR());
    }

    void updateLabels() {
        lblLogInName.setText(emp.getName());
    }

    public void setActiveEmployee(int id) {
        List<Report> dbreports = new ArrayList();
        helplines = dbm.getHelpLines();

        for (Helpline h : helplines) {
            h.loadAllEmployees();
            dbreports.addAll(dbm.getAllReports(h.getName()));

            for (Report r : dbreports) {
                Report report = reportExists(r);
                if (report != null) {
                    h.addReport(report);
                    report.addHelpline(h);
                } else {
                    reports.add(r);
                    h.addReport(r);
                    r.addHelpline(h);
                }
            }
            h.bindReportsToEmployees();

            dbreports.clear();
        }

        for (Helpline h : helplines) {
            for (Employee empl : h.getEmployees()) {
                if (empl.getBadgeNR() == (id)) {
                    Employee temp = h.getEmployeeWithID(id);
                    System.out.println(temp.getName());
                    emp = temp;
                }
            }
        }
    }

    private Report reportExists(Report r) {
        for (Report rep : reports) {
            if (rep.getReportID() == r.getReportID()) {
                return rep;
            }
        }
        return null;
    }

}
