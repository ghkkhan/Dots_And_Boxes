/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepackage;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public interface GameInterface {
    
    int checkBoxes(GameCircle c, GameCircle e, GameSquare A);
    void secondCircle(GameCircle c, double x, double y, GameSquare A,GameSquare B);
    void illegalMoveAlertBoxTemplate(String s);
    GameCircle checkClickCircle(double x, double y);
    int checkOrient(GameCircle B);
    
    public int getP1Score();
    public int getP2Score();
    
    void makeSquares();
    ArrayList<GameCircle> getCircleArray();
    void emptyList();
    
    void addToList(GameCircle c);
    double distForm(double x1, double y1, double x2, double y2);
}
