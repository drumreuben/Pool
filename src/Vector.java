/**
 * Created by Reuben on 4/3/2015.
 * very basic vector class
 */
public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getMagnitude(){
        return Math.sqrt((x * x) + (y * y));
    }

    public static double multiply(Vector v1, Vector v2){
        return (v1.getX() * v2.getX()) + (v1.getY() * v2.getY());
    }

    public static Vector multiply(Vector v1, double v2){
        return new Vector(v1.getX() * v2, v1.getY() * v2);
    }


    public void scale(double value){
        x *= 1/value;
        y *= 1/value;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    //returns a new vector formed by the addition of two vectors
    public Vector add(Vector v2) {
        return new Vector(this.x+v2.getX(), this.y + v2.getY());
    }


}
