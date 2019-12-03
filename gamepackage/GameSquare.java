/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepackage;

import hkg8bdotsandboxes.SysCom;

/**
 *
 * @author Huzaifa Khan, Hkg8b
 */
public class GameSquare extends SysCom {
    GameCircle uLeft, uRight, dLeft, dRight;
    boolean up, left, down, right;
    boolean isColored;
    boolean player1s;//if player1s ==true, then player 1 has this square... if not hten not...
    
    public GameSquare(GameCircle uLeft, GameCircle uRight, GameCircle dLeft, GameCircle dRight) {
        this.uLeft = uLeft;
        this.uRight = uRight;
        this.dLeft = dLeft;
        this.dRight = dRight;
        
        isColored = false;
        up = false;
        down = false;
        right = false;
        left = false;
    }
    
    boolean makeLine(GameCircle a, GameCircle b) {
        //lots of test cases...4 i guess...
        if(!up && checkCommon(a, b, uLeft, uRight)) {
            up = true;
            return true;
        }
        else if(!left && checkCommon(a, b, uLeft, dLeft)){
            left = true;
            return true;
        }
        else if(!right && checkCommon(a, b, uRight, dRight)) {
            right = true;
            return true;
        }
        else if(!down && checkCommon(a, b, dRight, dLeft)) {
            down = true;
            return true;
        }
        return false;
    }
    boolean checkForFilledResponse() {
        if(up && down && left && right && !isColored) {
            return true;
        }
        return false;
    }
    void fillSquare(boolean player1s) {
        this.isColored = true;
        this.player1s = player1s;
    }
    boolean checkCommon(GameCircle a, GameCircle b, GameCircle ASquare, GameCircle BSquare) {
        
        if(a.equals(ASquare) && b.equals(BSquare)) {
            return true;
        }
        else if (a.equals(BSquare) && b.equals(ASquare)) {
            return true;
        }
        return false;
    }
}