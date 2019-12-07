/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import hkg8bdotsandboxes.SysCom;
import static hkg8bdotsandboxes.SysCom.exitApp;
import static hkg8bdotsandboxes.SysCom.pf;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class RecordsController extends SysCom implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextArea textBox;
    @FXML
    private Button displayButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void openRecords(ActionEvent e) {
        
        try {
            //get number of lines:
            BufferedReader reader = new BufferedReader(new FileReader("records.txt"));
            int lines = 1;
            while (reader.readLine() != null) lines++;
            reader.close();
            
            textBox.setText("");
            reader = new BufferedReader(new FileReader("records.txt"));
            printRecordBackwards(reader, lines);
        }
        catch(IOException ex) {
            pf("There was an ioexception in the creating or reading of file.");
        }
        displayButton.setVisible(false);
        
    }
    private void printRecordBackwards(BufferedReader reader, int lines) throws IOException {
        String s = reader.readLine();
        if(s != null) {
            printRecordBackwards(reader, lines - 1);
            textBox.appendText((lines - 1)+": " + s + "\n");
        }
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
