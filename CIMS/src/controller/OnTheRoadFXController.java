
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        cc.setText(tfSendChat.getText());
    }

}
