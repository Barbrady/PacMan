import java.awt.*;
/**
 * Clase Cruces, comprueba si el elemento del laberinto es un cruce o codo.
 * Sirve para indicar a los fantasmas donde realizar un nuevo cálculo de distancia hacia
 * Comecocos.
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public class Cruces
{
    // instance variables - replace the example below with your own
    private int x,y;
    /**
     * Constructor for objects of class Cruces
     */
    public Cruces()
    {
       
    }

    public Cruces(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    public Rectangle crearRectangulo()
    {
        return new Rectangle(x*60,y*60,60,60);
    }
}
