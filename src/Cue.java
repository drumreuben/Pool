import javax.swing.*;
import java.awt.*;

/**
 * Created by Reuben on 4/4/2015.
 * The cue the player uses to hit the ball
 */
public class Cue {

    private boolean active = false;
    int x;
    int y;
    Table table;

    public Cue(Table table){
        this.table = table;
    }

    public void setActive(boolean state){
        active = state;
    }

    public boolean isActive(){
        return active;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillOval(x, y, 5, 5);
        g.drawLine((int)table.getBalls().get(0).getxPos(), (int)table.getBalls().get(0).getyPos(), x, y);
    }

}
