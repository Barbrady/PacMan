/**
 * Write a description of class Fantasma Blinky here.
 * 
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public class Blinky extends Fantasmas
{
    /**
     * Constructor for objects of class Fantasma Blinky
     */
    public Blinky()
    {
        super("blinky.gif", 240, 180);
    }
    
    public void finPanico()
    {
        super.finPanico();
        actualizarimagen("blinky.gif");
    }
    
    public void muerte (){
        super.muerte();
        x = 300;
        y = 300;
        dx=dy=0;
    }

}
