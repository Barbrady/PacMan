//import javax.swing.ImageIcon;
//import java.awt.Image;
import java.awt.*;
/**
 * Write a description of class Casilla here.
 * 
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public class Casilla

{
    // instance variables - replace the example below with your own
    private int x,y;
    private boolean cruce;
    /**
     * Constructor for objects of class Casilla
     */
    public Casilla()
    {
        // initialise instance variables
    }

    public Casilla(int x, int y)
    {
        this.x=x;
        this.y=y;
        cruce=true;
    }

    public int getx()
    {
        return x;
    }
    
    public int gety()
    {
        return y;
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public boolean comprobarCruce()
    {
        // put your code here
        return cruce;
    }
    
    public Rectangle crearRectangulo()
    {
        return new Rectangle(getx()*60,gety()*60,60,60);
    }
}
