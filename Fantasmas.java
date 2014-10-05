/**
 * Abstract class Fantasmas - write a description of the class here
 * 
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public abstract class Fantasmas extends Personajes
{
    protected boolean panico, cruce, muerte;
    public Fantasmas (String rutaimagen, int x, int y)
    {
       super(rutaimagen,x,y);
       //panico = false;
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
    
    public void move()
    {
        x+=dx;
        y+=dy;
        cruce=false;
    }
    
    public void muerte()
    {
        muerte=true;
    }
    
    public void cruce(){    
        cruce = true;
    }
    
    public void salir(){
        y=180;
        x=x-60;
        muerte = false;
    }
   }    

    
    

