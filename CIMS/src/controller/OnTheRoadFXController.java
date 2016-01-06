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
import javafx.event.Event;
import javafx.scene.control.*;

/**
 *
 * @author Jeroen Hendriks
 */
public class OnTheRoadFXController implements Initializable, Observer {

    private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
    private static String arg1;
    @FXML private MediaView mvTest;
    @FXML private TextField tfSendChat;
    @FXML private TextArea taChat;
    @FXML private Button btnSendChat;
    
    // information incident
    @FXML private Label lblTitle;
    @FXML private Label lblStartIncident;
    @FXML private Label lblDescription;
    @FXML private Label lblLocation;
    @FXML private Label lblGPS;
    @FXML private Label lblExtraInfoLoc;
    @FXML private Label lblApproach;
    
    // information login & partner
    @FXML private Label lblLogInName;
    @FXML private Label lblLogInFunction;
    @FXML private Label lblLogInStart;
    @FXML private Label lblPartnerName;
    @FXML private Label lblPartnerFunction;
    @FXML private Label lblPartnerStart;
    
    // information weather
    
    
    @FXML private TabPane tpImageVideo;
    // video
    @FXML private Tab tpgVideo;
    @FXML private TableView tvVideo;
    @FXML private TableColumn tcVideo;
    @FXML private Button btnRemoveVideo;
    @FXML private TextField tfVideo;
    @FXML private Button btnBrowseVideo;
    @FXML private Button btnAddVideo;
    // image
    @FXML private Tab tpgImage;
    @FXML private TableView tvImages;
    @FXML private TableColumn tcImage;
    @FXML private Button btnRemoveImage;
    @FXML private TextField tfImage;
    @FXML private Button btnBrowseImage;
    @FXML private Button btnAddImage;
    // play
    @FXML private Tab tpgPlay;
    @FXML private Button btnBack;
    

    ChatClient cc = new ChatClient(1);

    Thread chat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = new Media((arg1 != null) ? arg1 : MEDIA_URL);
        MediaPlayer mp = new MediaPlayer(media);
        //mp.setAutoPlay(true);
        mvTest.mediaPlayerProperty().set(mp);
        chat = new Thread(cc);
        chat.start();
        cc.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        taChat.setText(taChat.getText() + "\n" + ((ChatClient)o).getText());
    }

    public void btnSendChatClick(Event e) {
        cc.setText("On the Road: " + tfSendChat.getText());
    }

}