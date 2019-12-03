/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import hkg8bdotsandboxes.SysCom;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Huzaifa Khan, hkg8b
 */
public class AboutController extends SysCom implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    
    
    @FXML
    private void backButtonClicked(ActionEvent e) {
        backToMain((Stage) label.getScene().getWindow());
    }

    @FXML
    private void exitClicked(ActionEvent event) {
        exitApp();
    }
}
