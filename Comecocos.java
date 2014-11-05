/**
 * Write a description of class Pacman here.
 * 
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public class Comecocos extends Personajes
{  
    int direccionPendiente;
    private int vidas;
    /**
     * Constructor for objects of class Pacman
     */
    public Comecocos()
    {
        super("comecocos.gif", 420,660);
        direccionPendiente = 0;
        vidas = 3;
    }
    
    /**
     * Redefinimos el método move().
     */
    public void move(){
        comprobarPendiente();
        super.move();
    }
    
    public void moverArriba(){
        if(arriba && (x%60 == 0)){
         arriba();
         actualizarimagen("comecocos_up.gif");
         liberarDireccion();
         derecha=izquierda=false;
        }
        else{direccionPendiente=1;}
     }

    public void moverAbajo(){
        if(abajo && (x%60 == 0)){
         abajo();
         actualizarimagen("comecocos_down.gif");
         liberarDireccion();
         derecha=izquierda=false;
        }
        else{direccionPendiente=2;}
    }
    
    public void moverDerecha(){
       if(y%60 == 0){
        derecha();
        actualizarimagen("comecocos.gif");
        liberarDireccion();
        abajo=arriba=false;
       }
       else{direccionPendiente=3;}
    }
    
    public void moverIzquierda(){
       if(y%60 == 0){
       izquierda();
       actualizarimagen("comecocos_left.gif");
       liberarDireccion();
       abajo=arriba=false;
      }
      else{direccionPendiente=4;}
    }
    
    public void liberarDireccion(){
        direccionPendiente=0;
    }
    
    public void comprobarPendiente()
    {
        if(cruce){
            if(direccionPendiente == 1){moverArriba();}
            if(direccionPendiente == 2){moverAbajo();}
            if(direccionPendiente == 3){moverDerecha();}
            if(direccionPendiente == 4){moverIzquierda();}
        }
    }  
    
    public void matarComecocos()
    {
        vidas--;
        x=420;
        y=660;
    }
    
    public int vidasComecocos()
    {
        return vidas;
    }
    
}
