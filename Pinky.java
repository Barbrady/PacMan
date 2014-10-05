/**
 * Write a description of class Fantasma Pinky here.
 * 
  * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public class Pinky extends Fantasmas
{
    /**
     * Constructor for objects of class Fantasma Pinky
     */
    public Pinky()
    {
        super("pinky.gif", 780, 60);
    }
    
    public void muerte (){
        super.muerte();
        x = 420;
        y = 300;
        dx=dy=0;
    }
    
    public void finPanico()
    {
        super.finPanico();
        actualizarimagen("pinky.gif");
    } 
}