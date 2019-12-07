/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepackage;

import hkg8bdotsandboxes.SysCom;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Huzaifa Khan, hkg8b
 */

public class GameController extends SysCom implements Initializable, PropertyChangeListener {

    private GameModel gModel;
    
    private GraphicsContext gc;
    
    //fxml stuff
    @FXML
    private Canvas gBoard;
    @FXML
    private Label p1Name;
    @FXML
    private Label p2Name;
    @FXML
    private Label p1Score;
    @FXML
    private Label p2Score;
    
    @FXML
    private Button p1Surr; 
    @FXML
    private Button p2Surr;
    
    
    private final String RED = "#EE4466";
    private final String CYAN = "#00FFFF";
    private final String WHITE = "#DDDDDD";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        String name1 = "Player 1", name2 = "Player 2";
        //*****************************************
        //handle getting names from the user.
        
        name1 = nameGetterAlert(1);
        name2 = nameGetterAlert(2);
        
        //at this point, names have been gotten. next is setting up the board.
        gc = gBoard.getGraphicsContext2D();
        gModel = new GameModel();
        gModel.addPropertyChangeListener(this);
        setBoard();
        
        gBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            GameCircle gs = gModel.checkClickCircle(e.getX(), e.getY());
            if(gs != null) {
                activateCircle(gs);
            }
        });

        gModel.player1Name = name1;
        gModel.player2Name = name2;
        
        p1Name.setText(name1);
        p2Name.setText(name2);
        
        p1Name.setStyle("-fx-text-fill:" + CYAN);
        p2Name.setStyle("-fx-text-fill:" + WHITE);
        p2Surr.setVisible(false);
        //board setup complete. game ready to start.
    }
    @FXML
    private void p1Surrenders() {//player 1 has clicked their surrender button.
        runSurrender(gModel.player1Name, gModel.player2Name);
    }
    @FXML
    private void p2Surrenders() {//player 2 has clicked their surrender button.
        runSurrender(gModel.player2Name, gModel.player1Name);
    }
    
    private String nameGetterAlert(int i) {
        TextInputDialog textGetter = new TextInputDialog("");
        textGetter.setHeaderText("Enter your name, player "+i+"!\nNote: Clicking cancel will close the program.\nAlso, the name cannot be empty");
        Optional<String> res;
        while(true) {
            res = textGetter.showAndWait();
            if(res.isPresent()) {
                if(!textGetter.getEditor().getText().equals("")) {
                    return textGetter.getEditor().getText();
                }
            }
            else exitApp();
        }
    }
    
    private void runSurrender(String s, String a) {
        Alert al = new Alert(AlertType.CONFIRMATION);
        al.setTitle("Surrender Prompt");
        al.setHeaderText("Are you sure you'd like to surrender, " + s + "?");
        al.setContentText("This will be counted as your loss.");
        Optional<ButtonType> res;
        res = al.showAndWait();
        if(res.get() == ButtonType.OK) {
            //commence the victory/surrender screen.This is the same thing that shows when a person actulaly wins the full match.
            runCompletionSequence(a);
        }
        else {
            return;
        }
    }
    
    private void runCompletionSequence(String s) {
        Alert al = new Alert(AlertType.CONFIRMATION);
        al.setTitle("Congratulations!!");
        al.setHeaderText("Congratulations, player named " + s+"! You've won this round." );
        al.setContentText("Would you like to save this match?\nBear in mind that you can only save up to 20 matches.\nAfter this point, the earliest matches are deleted after every match.");
        Optional<ButtonType> res = al.showAndWait();
        if(res.get() == ButtonType.OK) {
            //save the match at the top of the file?....
            pf("Need to implement saving of the match data.");
            
            try {
                String str = gModel.player1Name + " vs. " + gModel.player2Name + " : "
                        + gModel.getP1Score() + " to " + gModel.getP2Score() + " respectively : : the victor is " + s;
                BufferedWriter writer = new BufferedWriter(new FileWriter("records.txt", true));
                writer.append(str + "\n");
                writer.close();
            }
            catch(IOException e) {
                pf("There was an ioexception in the creating or reading of file.");
            }
        }
        else {
            al = new Alert(AlertType.INFORMATION);
            al.setHeaderText("Alright, this match will be lost to the Annuls of History.");
            al.setContentText("You might wanna snap a picture while you still can.");
            al.setTitle("Match-Data-Purged");
            al.showAndWait();
        }
        //change the view back to the main screen.
//        pf("Change thte view back to the main SCreen");
        backToMain((Stage) gBoard.getScene().getWindow());
    }
    
    private void resetCircleBlack(GameCircle c) {
        gc.setFill(Color.BLACK);
        gc.fillOval(c.x, c.y, c.radius, c.radius);
        gModel.emptyList();
    }
    
    private void resetCircleBlue(GameCircle c){
        gc.setFill(Color.BLUE);
        gc.fillOval(c.x, c.y, c.radius, c.radius);
        gModel.emptyList();
    }
    
    private void activateCircle(GameCircle c) {
        gc.setFill(Color.RED);
        gc.fillOval(c.x, c.y, c.radius, c.radius);
    }
    private void drawLine(GameCircle a, GameCircle b) {
        gc.setFill(Color.BLUE);
        gc.fillOval(a.x, a.y, a.radius, a.radius);
        gc.fillOval(b.x, b.y, b.radius, b.radius);
        gc.strokeLine(a.x+a.radius/2, a.y+a.radius/2, b.x+b.radius/2, b.y+b.radius/2);
        resetCircleBlue(a);
        resetCircleBlue(b);
        gModel.emptyList();
    }
    private void reBroadcastScores(int left, int right) {
        p1Score.setText("" + left);
        p2Score.setText("" + right);
    }
    private void setBoard() {
        gc.setFill(Color.BLACK);
        int x, y, radius = 20;
        int xCord = 0, yCord = 0;
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                y = 75 + 80 * i;
                x =  125 + 80 * j;
                gc.fillOval(x, y, radius, radius);
                gModel.addToList(new GameCircle(x,y,radius, xCord, yCord));
                xCord += 1;
            }
            yCord += 1;
            xCord = 0;
        }
        
        gModel.makeSquares();
        gc.setStroke(Color.WHITE);
        gc.strokeRect(0, 0, 1000, 900);
    }
    private void colorSquare(GameSquare A, boolean turn) {
        if(turn) gc.setFill(Color.CYAN);
        else gc.setFill(Color.CRIMSON);
        gc.fillRect(A.uLeft.x+A.uLeft.radius/2-1, A.uLeft.y+A.uLeft.radius/2-1,82, 82);
        resetCircleBlue(A.uLeft);
        resetCircleBlue(A.uRight);
        resetCircleBlue(A.dLeft);
        resetCircleBlue(A.dRight);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(((String)evt.getPropertyName()).equals("Make Line")) {
            drawLine(gModel.circlesInPlay[0] , gModel.circlesInPlay[1]);
        }
        if(((String)evt.getPropertyName()).equals("Paint it Black")) {
            resetCircleBlack(gModel.circlesInPlay[0]);
        }
        if(((String)evt.getPropertyName()).equals("Paint it Blue")) {
            resetCircleBlue(gModel.circlesInPlay[0]);
        }
        if(evt.getPropertyName().equals("score")) {
            reBroadcastScores((int)evt.getOldValue(),(int) evt.getNewValue());
        }
        if(evt.getPropertyName().equals("colorSquare")) {
            colorSquare((GameSquare)evt.getOldValue(), (boolean)evt.getNewValue());
        }
        if(evt.getPropertyName().equals("PlayerSwitch")) {
            if((boolean)evt.getNewValue()) {
                p1Name.setStyle("-fx-text-fill:" + CYAN);
                p2Name.setStyle("-fx-text-fill:" + WHITE);
                p1Surr.setVisible(true);
                p2Surr.setVisible(false);
            }
            else {
                p1Name.setStyle("-fx-text-fill:" + WHITE);
                p2Name.setStyle("-fx-text-fill:" + RED);
                p1Surr.setVisible(false);
                p2Surr.setVisible(true);
            }
        }
        if(evt.getPropertyName().equals("gameEnd")) {
            runCompletionSequence((String)evt.getNewValue());
        }
    }
}