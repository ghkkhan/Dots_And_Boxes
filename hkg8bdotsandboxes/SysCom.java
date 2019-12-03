/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkg8bdotsandboxes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public abstract class SysCom {
    
    public void backToMain(Stage s) {
        changeScene("/fxmlcontainer/FXMLDocument.fxml", s);
    }
    
    public static void exitApp() {
        Platform.exit();
        pf("got here...");
        System.exit(0);
    }
    
    public void changeScene(String s, Stage stage) {
        
        Parent root;
        Scene sc;
        try {
            root = FXMLLoader.load(getClass().getResource(s));
            sc = new Scene(root);
            sc.getStylesheets().add(getClass().getResource("mainCss.css").toExternalForm());
            stage.setScene(sc);
        } catch (IOException ex) {
            Logger.getLogger(SysCom.class.getName()).log(Level.SEVERE, null, ex);
            exitApp();
        }
    }
    
    public static void pf(String s){
        System.err.println(s);
    }
}
