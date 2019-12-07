/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import hkg8bdotsandboxes.SysCom;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Huzaifa Khan, hkg8b
 */
public class FXMLDocumentController extends SysCom implements Initializable {
    
    @FXML
    private Label label;
    
    private final String GAME_FILE = "/fxmlcontainer/Game.fxml";
    private final String ABOUT_FILE = "/fxmlcontainer/About.fxml";
    private final String HELP_FILE = "/fxmlcontainer/Help.fxml";
    private final String RECORD_FILE = "/fxmlcontainer/Records.fxml";
    
    @FXML
    private void startGameButton() {
        changeScene(GAME_FILE, (Stage) label.getScene().getWindow());
    }
    @FXML
    private void aboutButton() {
        changeScene(ABOUT_FILE, (Stage) label.getScene().getWindow());
    }
    
    @FXML
    private void recordButton() {
        changeScene(RECORD_FILE, (Stage) label.getScene().getWindow());
    }    
    
    @FXML
    private void exitButton() {
        exitApp();
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
    
    }
}