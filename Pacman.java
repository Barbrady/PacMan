import javax.swing.JFrame;
/**
* Clase principal que general el juego.
* Se crea un JFrame, se le asignan ciertos valores y se añade un objeto VistaControlador
* @author Aitor Alcorta
* @version 1.0.15052014
*/
public class Pacman extends JFrame
{
   private int alto, ancho; //Variables para las dimensiones del JFrame
   public Pacman()
    {
       alto = 1200;
       ancho = 1440;        
       setSize(ancho, alto);
       setResizable(true);
       setFocusable(false);
       add(new VistaControlador());//Añadimos la clase VistaControlador, que extiende JPanel, al JFrame.
       setVisible(true);//Hacemos visible el JFrame. 
    }
    
   public static void main (String[]args)
   {
       new Pacman();
   }
}
        