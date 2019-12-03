/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepackage;

/**
 *
 * @author Huzaifa Khan, hkg8b
 */

import hkg8bdotsandboxes.SysCom;
import static hkg8bdotsandboxes.SysCom.pf;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameModel extends AbstractModel{
    private final int num = 9;
    
    private ArrayList<GameCircle> circleArray = new ArrayList<>();//keeps track of all the circles... to be moved to Model
    private ArrayList<GameCircle> circlesOutOfPlay = new ArrayList<>();//keeps track of circles that are done...
    
    GameCircle[] circlesInPlay = {null, null};//keeps track of two circles selected.
    private GameSquare[][] squArray = new GameSquare[num][num];

    private boolean firstCircle = true;
    private boolean player1Turn = true;
    
    private int player1Score=0;
    private int player2Score=0;
    
    int checkBoxes(GameCircle c, GameCircle e, GameSquare A) {
        if(A.makeLine(c, e)) {
            if(A.checkForFilledResponse()) {
                //this means that the square has all the sides filled.. Its filled.
                A.fillSquare(player1Turn);
                firePropertyChange("colorSquare", A, player1Turn);
                if(player1Turn) player1Score++;
                else player2Score++;
                firePropertyChange("score", player1Score, player2Score);
                return 1;
            }
            return 0;
        }
        else return -255;
    }
    
    void secondCircle(GameCircle c, double x, double y, GameSquare A, GameSquare B) {
        GameCircle e = circlesInPlay[0];

        if(distForm(e.x, e.y, c.x, c.y) <= 85) {
            circlesInPlay[1] = c;
            firstCircle = true;
            circlesOutOfPlay.add(c);
            circlesOutOfPlay.add(e);
            int test = 0;
            
            if(A != null) test += checkBoxes(c, e, A);
            if(B != null) test += checkBoxes(c, e, B);
            if(test == 0) {
                firePropertyChange("Make Line",null, null);
                player1Turn = !player1Turn;//switches the players turn;
                firePropertyChange("PlayerSwitch", null, player1Turn);
            }
            else if(test < -10) {
                //warn players that they can't make the same line twice.
                pf("Can't draw the same line Twice! Here, try again.");
                
                Alert al = new Alert(AlertType.ERROR);
                al.setTitle("Illegal MOVE!");
                al.setHeaderText("That move just now was Illegal.");
                al.setContentText("You know the rules! I believe in you! Try again...");
                
                firstCircle = true;
                if(circlesOutOfPlay.contains(c))    firePropertyChange("Paint it Blue", null, null);
                else                                firePropertyChange("Paint it Black", null, null);
                    
            }
        }
        else {
//            warn player that they can only click the first
//            circle or an adjacent, non-diagonal circle.
//            Alert box or somehing.
        }
    }
    GameCircle checkClickCircle(double x, double y) { 
        GameCircle clickedCircle = null;
        for(GameCircle c : circleArray) {
            if(c.checkClick(x, y) == true) {
                clickedCircle = c;
                break;
            }
        }
        if(clickedCircle == null) {
            return null;
        }
        //check if first circle was clicked again, instead of a new circle.
        if(firstCircle) {
            circlesInPlay[0] = clickedCircle;
            firstCircle = !firstCircle;
            return clickedCircle;
        }
        else {
            int orient = checkOrient(clickedCircle);
            GameSquare A = null, B = null;
            int idA = 0, idB = 0;
            switch(orient) {
                case 0: 
                    //case for the two circles to be the same.
                    firstCircle = true;
                    if(circlesOutOfPlay.contains(clickedCircle)) {
                        firePropertyChange("Paint it Blue", null, null);
                    }
                    else {
                        firePropertyChange("Paint it Black", null, null);
                    }
                    return null;
                case 1:
                    //case for circle to be north of the first one
                    //a is left, b is right
                    if(clickedCircle.xCoordinate == 0) {
                        B = squArray[clickedCircle.yCoordinate][clickedCircle.xCoordinate];
                    }
                    else if(clickedCircle.xCoordinate == 9) {
                        A = squArray[clickedCircle.yCoordinate][clickedCircle.xCoordinate - 1];
                    }
                    else {
                        A = squArray[clickedCircle.yCoordinate][clickedCircle.xCoordinate - 1];
                        B = squArray[clickedCircle.yCoordinate][clickedCircle.xCoordinate];
                    }
                    break;
                case 2:
                    //case for circle to be east of the first one
                    //in this case A is up, B is down/
                    if(clickedCircle.yCoordinate == 0) {

                        B = squArray[clickedCircle.yCoordinate][clickedCircle.xCoordinate - 1];
                    }
                    else if(clickedCircle.yCoordinate == 9) {
                        A = squArray[clickedCircle.yCoordinate - 1][clickedCircle.xCoordinate - 1];                        
                    }
                    else {

                        A = squArray[clickedCircle.yCoordinate - 1][clickedCircle.xCoordinate - 1];
                        B = squArray[clickedCircle.yCoordinate][clickedCircle.xCoordinate - 1];
                    }
                    break;
                case 3: //case for the circle to be west of the first one
                    if(clickedCircle.yCoordinate == 0) {
                        B = squArray[clickedCircle.yCoordinate][clickedCircle.xCoordinate];
                    }
                    else if(clickedCircle.yCoordinate == 9) {
                        A = squArray[clickedCircle.yCoordinate - 1][clickedCircle.xCoordinate];                        
                    }
                    else {
                        A = squArray[clickedCircle.yCoordinate - 1][clickedCircle.xCoordinate];
                        B = squArray[clickedCircle.yCoordinate][clickedCircle.xCoordinate];
                    }
                    break;
                case 4: //case for the cirlce to be south of the first one.
                    
                    if(clickedCircle.xCoordinate == 0) {
                        B = squArray[clickedCircle.yCoordinate-1][clickedCircle.xCoordinate];
                    }
                    else if(clickedCircle.xCoordinate == 9) {
                        A = squArray[clickedCircle.yCoordinate - 1][clickedCircle.xCoordinate - 1];
                    }
                    else {
                        A = squArray[clickedCircle.yCoordinate - 1][clickedCircle.xCoordinate - 1];
                        B = squArray[clickedCircle.yCoordinate-1][clickedCircle.xCoordinate];
                    }
                    break;
                default: 
                    SysCom.pf("Lol wut : : Orient == " + orient);
            }
            secondCircle(clickedCircle, x, y, A, B);
            A = null;
            B = null;
        }
        return null;
    }
    
    int checkOrient(GameCircle B) {
        int ans = -1;
        GameCircle A = circlesInPlay[0];
        
        if(A.x == B.x) {
            if(A.y == B.y) {
                ans = 0;
            }
            else if(A.y > B.y) {
                ans = 1;
            }
            else {
                ans = 4;
            }
        }
        else {//at this point, it is presumed that A.y == B.y.
            if(A.x < B.x) {
                ans = 2;
            }
            else {
                ans = 3;
            }
        }
        return ans;
    }
    
    
//  *****************************************************************************************************
    //initialize functions. Used to set up the board and the data storage behind it.
    void makeSquares() {
        for(int i = 0; i < num; i++) {
            for(int j = 0; j < num; j++) {
                squArray[i][j] = new GameSquare(circleArray.get(j+i*10), circleArray.get(j + i*10 + 1),
                    circleArray.get(j + (i+1) * 10), circleArray.get(j + (i+1) *10 + 1));
            }
        }
    }
    ArrayList<GameCircle> getCircleArray() {
        return circleArray;
    }
    void emptyList() {
        circlesInPlay[0] = null;
        circlesInPlay[1] = null;
    }
    
    void addToList(GameCircle c) {
        circleArray.add(c);
    }
    double distForm(double x1, double y1, double x2, double y2) {
        double ans;
        ans = Math.pow(x1-x2, 2) + Math.pow(y1-y2,2);
        ans = Math.sqrt(ans);
        return ans;
    }
}