import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Reuben on 4/4/2015.
 * a pool table. has balls on it.handles collisions and user interaction.
 */
public class Table {

    //length and width
    private int width;
    private int length;

    final static double FRICTION = 1.001;

    //Swing stuff
    private JPanel panel;
    private final JFrame window;
    private MouseListener m;
    private MouseMotionListener mouseMotion;
    private final long animationSpeed = 2;

    //all the balls on the table
    private ArrayList<Ball> balls;

    //the pockets of the table
    private ArrayList<Pocket> pockets;

    public ArrayList<Pocket> getPockets() {
        return pockets;
    }

    //the players cue
    private Cue cue = new Cue(this);

    //finals
    final double BALL_TO_TABLE_RATIO = 46.178861788617886178861788617886178861788617886178861;
    final static int TABLE_EDGE_WIDTH = 45;

    int playerTurn = 0;

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Cue getCue(){ return cue;}

    public JFrame getWindow(){ return window;};

    public int getCurrentPlayer(){
        if(playerTurn % 2 == 0){
            return 1;
        } else {
            return 2;
        }
    }

    public Table(int length){

        //establishes length and width of the table based on a 2 : 1 ratio
        this.length = length;
        this.width = length /2;

        //makes a Jframe to contain the tables and configures it
        window = new JFrame("Pool Table Window");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //arraylist to hold all the balls on the table
        balls = new ArrayList<Ball>();

        //makes a cueball
        final Ball cueBall = new Ball(350, 250, (int)(length / BALL_TO_TABLE_RATIO / 2), Color.WHITE, - 0, 0);
        balls.add(cueBall);
        balls.add( new Ball(140, 250, (int)(length / BALL_TO_TABLE_RATIO / 2), new Color(250,167,0), 0, 0));
        balls.add( new Ball(120, 265.2, (int)(length / BALL_TO_TABLE_RATIO / 2), new Color(34,56,158) , 0, 0));
        balls.add( new Ball(120, 235.1, (int)(length / BALL_TO_TABLE_RATIO / 2), new Color(233,34,29), 0, 0));
        balls.add( new Ball(100, 280.3, (int)(length / BALL_TO_TABLE_RATIO / 2), new Color(43,35,86), 0, 0));
        balls.add( new Ball(100, 250.5, (int)(length / BALL_TO_TABLE_RATIO / 2), new Color(254,99,32), 0, 0));
        balls.add( new Ball(100, 220.4, (int)(length / BALL_TO_TABLE_RATIO / 2), new Color(152,21,26), 0, 0));

        //makes the pockets


        //initializes the panel, adds two Jframe, and packs everything
        panel = new myGraphicPanel(this);
        window.getContentPane().add("Center", panel);
        window.setSize(new Dimension(length, width));
        window.setResizable(false);
        window.setVisible(true);

        Player player1 = new Player("PLayer 1");
        Player player2 = new Player("PLayer 1");
    }

    public void play(){

        boolean foundMotion;
        boolean shooting = false;

        m = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(cue.isActive()){
                    Vector newVelocity = new Vector(balls.get(0).getxPos() - e.getX(), balls.get(0).getyPos() - e.getY());
                    newVelocity.scale(100);
                balls.get(0).setVelocity(newVelocity);
                }
            }
        };

        mouseMotion = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cue.setX(e.getX());
                cue.setY(e.getY());
            }
        };


        window.addMouseMotionListener(mouseMotion);
        window.addMouseListener(m);


        //main game loop
        while(true){

            if(shooting){
                boolean aiming = true;
                cue.setActive(true);
                int i = 10000;
                while(aiming){
                    if(balls.get(0).getVelocity().getX() != 0 || balls.get(0).getVelocity().getY() != 0){
                        aiming = false;
                        shooting = false;
                    }
                    panel.repaint();
                }
            } else {
                foundMotion = true;
                cue.setActive(false);
                while(foundMotion) {
                    foundMotion = false;
                    //interaction and velocity update for each ball on the table
                    for(Ball b : balls){
                        if(b.step()){
                            foundMotion = true;
                        }
                    }
                    for(Ball b : balls){
                        //checks collision
                        collide();
                        //updates position
                    }

                    //repaints the jPanel
                    panel.repaint();
                    //you are feeling sleeeeeepy.....
                    try{
                        Thread.sleep(animationSpeed);
                    } catch (InterruptedException e){
                        //there was an error!
                    }
                }
                shooting = true;
            }
            playerTurn++;
        }
    }

    public void collide(){

        //checks for each ball on the table
        for(Ball b : balls) {

            //checks edge collision first
            b.checkEdgeCollision(this);
            for(Ball b2 : balls){
                if(b != b2) {
                    b.checkBallCollision(b2);
                }
            }
        }
    }
}
