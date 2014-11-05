import java.util.Random;
/**
 * Abstract class Fantasmas - write a description of the class here
 * 
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */

public abstract class Fantasmas extends Personajes
{
    protected boolean panico, muerte;
    protected Random aleatorio;
    public Fantasmas (String rutaimagen, int x, int y)
    {
       super(rutaimagen,x,y);
    }
  
    public void panico()
    {
       super.actualizarimagen("fantasma_panico.gif");
       panico = true;
    }

    public void finPanico()
    {
       panico = false; 
    }

    public void muerte()
    {
        muerte=true;
    }
   
    public void salir(){
        y=180;
        x=x-60;
        muerte = false;
    }
    
    public void inteligenciaArtificial(int comecocosX, int comecocosY){
       aleatorio = new Random();
    }  
}

    
    

