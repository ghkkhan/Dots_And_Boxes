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
import java.util.Optional;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;


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
    
    private final String RED = "#EE4466";
    private final String WHITE = "#DDDDDD";
    
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
                
        String name1 = "Player 1", name2 = "Player 2";
        //*****************************************
        //handle getting names from the user.
        
        TextInputDialog textGetter = new TextInputDialog("");
        textGetter.setHeaderText("Enter your name, player 1!");
        Optional<String> res;
        while(true) {
            res = textGetter.showAndWait();
            if(res.isPresent()) {
                if(!textGetter.getEditor().getText().equals("")) {
                    name1 = textGetter.getEditor().getText();
                    break;
                }
            }
            else {
                //code to go back to the main menu...
                exitApp();
//                return;
//                backToMain((Stage) p2Score.getScene().getWindow());
            }
        }
        textGetter.getEditor().setText("");
        textGetter.setHeaderText("Enter your name, player 2!");
        while(true) {
            res = textGetter.showAndWait();
            if(res.isPresent()) {
                if(!textGetter.getEditor().getText().equals("")) {
                    name2 = textGetter.getEditor().getText();
                    break;
                }
            }
            else {
                //code to go back to the main menu...
                exitApp();
//                return;
//                backToMain((Stage) p2Score.getScene().getWindow());
            }
        }
        
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

        
        p1Name.setText(name1);
        p2Name.setText(name2);
        
        p1Name.setStyle("-fx-text-fill:" + RED);
        p2Name.setStyle("-fx-text-fill:" + WHITE);

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
                p1Name.setStyle("-fx-text-fill:" + RED);
                p2Name.setStyle("-fx-text-fill:" + WHITE);
            }
            else {
                p1Name.setStyle("-fx-text-fill:" + WHITE);
                p2Name.setStyle("-fx-text-fill:" + RED);
            }
        }
    }
}