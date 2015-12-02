/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author Jeroen Hendriks
 */
public class OnTheRoadFXController implements Initializable {
    private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
    private static String arg1;
    @FXML MediaView mvTest;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = new Media((arg1 != null) ? arg1 : MEDIA_URL);
        MediaPlayer mp = new MediaPlayer(media);
        mp.setAutoPlay(true);
        mvTest.mediaPlayerProperty().set(mp);
    }
}
