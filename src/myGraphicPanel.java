/**
 * Created by Reuben on 4/3/2015.
 * Handles graphics components of a simple pool game.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class myGraphicPanel extends JPanel {

    Table table;
    Image tableSurface;

    public myGraphicPanel(Table table) {
        this.table = table;
        setPreferredSize(new Dimension(table.getLength()+10, table.getWidth() + 40));
        try{
            tableSurface = ImageIO.read(new File("C:\\Users\\Reuben\\Desktop\\IdeaProjects\\Pool\\src\\PoolTableReferenceTop.png"));
        } catch (IOException e){
            //foo
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(tableSurface, 0, 0, table.getLength(), table.getWindow().getContentPane().getHeight(), 0, 0,  tableSurface.getWidth(null), tableSurface.getHeight(null), null);
        for(Ball b : table.getBalls()){
            b.draw(g);
        }
        if(table.getCue().isActive()){
            table.getCue().draw(g);
        }
    }

}
