/**
 * Write a description of class Fantasma Clyde here.
 * 
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public class Clyde extends Fantasmas
{
    /**
     * Constructor for objects of class Clyde
     */
    public Clyde()
    {
        super("clyde.gif", 480, 180);
    }
    
    public void finPanico()
    {
        super.finPanico();
        actualizarimagen("clyde.gif");
    } 
    
    public void muerte(){
        super.muerte();
        x = 540;
        y = 300;
        dx=dy=0;
    }
    
    public void inteligenciaArtificial(int comecocosX, int comecocosY)
    {
        super.inteligenciaArtificial(comecocosX,comecocosY);
        int movimiento = aleatorio.nextInt(3);
        if(!muerte && cruce){
                switch(movimiento){
                     case 0:
                     if(arriba){
                      arriba();
                      break;}
                     case 1:
                     if(abajo){
                      abajo();
                      break;}
                     case 2:
                     if(derecha){
                      derecha();
                      break;}
                     case 3:
                     if(izquierda){
                      izquierda();
                      break;
                     }
                    }
                }
        }
            
}