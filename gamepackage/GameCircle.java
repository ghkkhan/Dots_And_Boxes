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

public class GameCircle {
    double x;//x value of the center
    double y;//y value of the center
    int radius;//radius of the circle
    
    int xCoordinate;
    int yCoordinate;
    
    public GameCircle(double x, double y, int radius, int xCoordinate, int yCoordinate) {
        this.x = x;
        this.y = y;
        this.radius = radius;
 
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    
    boolean checkClick(double x, double y) {
        if(distForm(x,y,this.x, this.y) <= this.radius + 2) {
            return true;
        }
        return false;
    }
    double distForm(double x1, double y1, double x2, double y2) {
        double ans;
        ans = Math.pow(x1-x2, 2) + Math.pow(y1-y2,2);
        ans = Math.sqrt(ans);
        return ans;
    }

    boolean equals(GameCircle B) {
        GameCircle a = this;
        if(a.x == B.x && a.y == B.y) return true;
        else return false;
    }
}