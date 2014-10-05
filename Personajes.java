import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.*;

/**
 * Abstract class Personajes - Clase con las variables y métodos comunes a los cuatro personajes
 * Clase abstracta Personajes, de ella heredan los personajes que actuan en el juego
 * Pacman y los tres fantasmas, Blinky, Pinky y Clyde.
  * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public abstract class Personajes
{
    // Variables de instancia - 
    private String rutaimagen;  //Define la ruta de la imagen gif del personaje
    protected int x,y,dx,dy;    //Las variables de las coordenadas x e y. Desplazamiento x e y.
    private Image imagen;       //La variable imagen donde irá la imagen de la figura
    private ImageIcon imageIcon;

    /**
     * Constructor vacío
     */
    public Personajes ()
    {
        
    }
    
    /**
     * Constructor de los personajes, al que se le pasa la ruta de la imagen, y las coordenadas.
     */
    public Personajes (String rutaimagen, int x, int y)
    {
        this.x = x;
        this.y = y;
        imageIcon = new ImageIcon(this.getClass().getResource(rutaimagen));
        imagen = imageIcon.getImage();
    }
    
    /**
     * Método que devuelve la posición X del personaje.
     */
    public int getx()
    {
        return x;
    }
    
    /**
     * Método que devuelve la posición Y del personaje.
     */
    public int gety()
    {
        return y;
    }

      /**
     * Método que devuelve un objeto Image, con la imagen del personaje.
     * Este método se ejecuta por el método paint redefinido en la clase VistaControlador.
     * Se pinta en el frame la imagen del personaje.
     */
    public Image getImagen()
    {
        return imagen;
    }
    
    /**
     * Método move, para mover los personajes por el JFrame
     * 
     */
    public void move()
    {
        x +=dx;
        y +=dy;  
    }
    
    /**
     * Método para parar los personajes cuando hay una colisión.
     */
    public void stop()
    {
        x -= dx;
        y -= dy;
    }
  
    /**
     * Método que cambia de dirección al personaje.
     */
    public void volver()
    {
        dx = dx*(-1);
        dy = dy*(-1);
    }
    
    /**
     * Método para mover al personaje hacia arriba, indicando la velocidad en el eje Y.
     */
    public void arriba()
    {
        dy=-30;
        dx=0;
    }
    
    /**
     * Método para mover al personaje hacia abajo, indicando la velocidad en el eje Y.
     */
    public void abajo()
    {
        dy=30;
        dx=0;
    }
    
    /**
     * Método para mover al personaje hacia la derecha, indicando la velocidad en el eje X.
     */
    public void derecha()
    {
        dx=30;
        dy=0;
    }
    
    /**
     * Método para mover al personaje hacia la izquierda, indicando la velocidad en el eje X.
     */
    public void izquierda()
    {
        dx=-30;
        dy=0;
    }
 
    /**
     * Método para actualizar la imagen del personaje cuando hay algún evento.
     * Este método recibe un String con la dirección de la nueva imagen.
     */
    public void actualizarimagen(String rutaimagen)
    {
        imageIcon = new ImageIcon(this.getClass().getResource(rutaimagen));
        imagen = imageIcon.getImage();
    }
    
    public int getCasillax()
    {
        int x=this.x/60;
        return x;
    }
    
    public int getCasillay()
    {
        int y=this.y/60;
        return y;
    }
    
    /**
     * Crea un rectangulo en la posición que ocupa el personaje, usado para la lógica de detectar colisiones.
     * El método devuevle un objeto rectángulo.
     */
    public Rectangle crearRectangulo()
    {
        return new Rectangle(getx(),gety(),60,60);
    }
}
