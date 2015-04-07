import java.awt.*;
import java.net.MalformedURLException;
import java.util.Calendar;

/**
 * Created by Reuben on 4/3/2015.
 * a billiard ball
 */
public class Ball {

    private double xPos;
    private double yPos;
    private Color color;
    private int radius;
    Vector velocity;

    private final double tolerance = .02;

    public Ball(double xPos, double yPos, int radius, Color color, double xVel, double yVel) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.color = color;
        velocity = new Vector(xVel, yVel);
    }
    public Ball(double xPos, double yPos, int radius, Color color) {
       this(xPos, yPos, radius, color, Math.random() * 2 - 1, Math.random() * 2 - 1);
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public Color getColor() {
        return color;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getxVel(){
        return velocity.getX();
    }

    public double getyVel(){
        return velocity.getY();
    }

    public void setxVel(double xVel){
        velocity.setX(xVel);
    }

    public void setyVel(double yVel){
        velocity.setY(yVel);
    }

    public int getRadius() {
        return radius;
    }

    public boolean step(){
        //updates positions
        xPos += velocity.getX();
        yPos += velocity.getY();

        //applies friction
        velocity.scale(Table.FRICTION);
        if(Math.abs(velocity.getMagnitude()) < tolerance){
            velocity = new Vector(0, 0);
        }

        return getxVel() != 0 || getyVel() != 0;

    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval((int) xPos - radius, (int) yPos - radius, radius*2, radius*2);
    }

    //checks collision with the edge of a Table
    public void checkEdgeCollision(Table t){

        //x collision detection with table edges
        if(xPos + radius + getxVel() >= t.getWindow().getContentPane().getWidth() - Table.TABLE_EDGE_WIDTH || xPos - radius + getxVel()  <= Table.TABLE_EDGE_WIDTH){
            setxVel(getxVel() * -1);
        }

        //y collision detection with table edges
        if(yPos + radius + getyVel() >= t.getWindow().getContentPane().getHeight() - Table.TABLE_EDGE_WIDTH + 3 || yPos - radius + getyVel() <= Table.TABLE_EDGE_WIDTH - 3){
            setyVel(getyVel() * -1);
        }
    }

    //checks collision with another ball
    public void checkBallCollision(Ball b2){

        if(roughCollision(b2)){
            if(fineCollision(b2)){
                collideWithBall(b2);
            }
        }
    }

    //rough collision detection (rectangle Based)
    public boolean roughCollision(Ball b2){
        return (xPos + radius > b2.getxPos() - b2.getRadius()
                && xPos - radius < b2.getxPos() + b2.getRadius()
                && yPos + radius > b2.getyPos() - b2.getRadius()
                && yPos - radius < b2.getyPos() + b2.getRadius());
    }

    //fine collision detection (radius based)
    public boolean fineCollision(Ball b2){
        double distance = Math.sqrt(Math.pow(xPos - b2.xPos, 2) + Math.pow(yPos - b2.yPos, 2));
        return distance < radius + b2.radius;
    }

    public void collideWithBall(Ball b2) {

        Vector normal = new Vector(b2.getxPos() - xPos, b2.getyPos() - yPos);
        Vector unitNormal = new Vector(normal.getX() / normal.getMagnitude(), normal.getY() / normal.getMagnitude());
        Vector unitTangent = new Vector(-1 * unitNormal.getY(), unitNormal.getX());
        double vel1normal = Vector.multiply(getVelocity(),unitNormal);
        double vel1tangential = Vector.multiply(getVelocity(),unitTangent);
        double vel2normal = Vector.multiply(b2.getVelocity(),unitNormal);
        double vel2tangential = Vector.multiply(b2.getVelocity(),unitTangent);
        double vel1NormalNew = (2 * b2.getRadius() * vel2normal) / (getRadius() + b2.getRadius());
        double vel1TangentialNew = vel1tangential;
        double vel2NormalNew = (2 * getRadius() * vel1normal) / (getRadius() + b2.getRadius());
        double vel2TangentialNew = vel2tangential;
        Vector finalNormal1 = Vector.multiply(unitNormal, vel1NormalNew);
        Vector finalTangential1 = Vector.multiply(unitTangent, vel1TangentialNew);
        Vector finalNormal2 = Vector.multiply(unitNormal, vel2NormalNew);
        Vector finalTangential2 = Vector.multiply(unitTangent, vel2TangentialNew);
        Vector finalVelocity1 = finalNormal1.add(finalTangential1);
        Vector finalVelocity2 = finalNormal2.add(finalTangential2);

        this.velocity = finalVelocity1;
        b2.setVelocity(finalVelocity2);

        while(fineCollision(b2)){
            step();
            b2.step();
        }
    }
}

