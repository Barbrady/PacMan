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
    
    public void muerte(){
        super.muerte();
        x = 300;
        y = 300;
        dx=dy=0;
    }

     public void inteligenciaArtificial(int comecocosX, int comecocosY){
        super.inteligenciaArtificial(comecocosX,comecocosY);
        int movimiento = aleatorio.nextInt(3);
        if(!muerte && cruce){
          if(comecocosY - gety() < 0){ //Si Comecocos se encuentra arriba de Blinky
                 if(!arriba || panico){ //Si la casilla de arriba de Blinky es un muro
                       switch (movimiento){
                          case 0:
                          derecha();
                          break;
                          case 1:
                          izquierda();
                           break;
                           case 2:
                           derecha();
                           break;
                           case 3:
                           izquierda();
                           break;
                        }
                    }
                 if(arriba){ //Si la casilla de arriba de Blynki no es un muro
                     if(!panico){arriba();}
                     if(panico && abajo){abajo();}
                    }
                }
                 
          if(comecocosY - gety() > 0){ //Si Comecocos se encuentra debajo de Pinky
                 if(!abajo || panico){ //Si la casilla de abajo de Blinky es un muro
                     switch (movimiento){
                           case 0:
                           derecha();
                           break;
                           case 1:
                           izquierda();
                           break;
                           case 2:
                           derecha();
                           break;
                           case 3:
                           izquierda();
                           break;
                        }
                    }
                 if(abajo){ //Si la casilla de abajo de Blinky no es un muro
                     if(!panico){abajo();}
                     if(panico && arriba){arriba();}
                   }
                }
                   
          if(!muerte && cruce){
               if(comecocosX - getx() < 0){ //Si Comecocos se encuentra a la izquierda de Blinky
                 if(!izquierda || panico){ //Si la casilla de la izquierda de Blinky es un muro
                     switch (movimiento){
                           case 0:
                           arriba();
                           break;
                           case 1:
                           abajo();
                           break;
                           case 2:
                           arriba();
                           break;
                           case 3:
                           abajo();
                           break;
                        }
                 }
                 if(izquierda){ //Si la casilla de la izquierda de Blinky no es un muro
                     if(!panico){izquierda();}
                     if(panico && derecha){derecha();}
                    }
                }
                 
               if(comecocosX -  getx() > 0){  //Si Comecocos se encuentra a la derecha de Blinky
                 if(!derecha || panico){ //Si la casilla de la derecya de Blinky es un muro
                     switch (movimiento){
                           case 0:
                           arriba();
                           break;
                           case 1:
                           abajo();
                           break;
                           case 2:
                           arriba();
                           break;
                           case 3:
                           abajo();
                           break;
                        }
                 }
                 if(derecha){ //Si la casilla de la derecha de Blinky no es un muro
                     if(!panico){derecha();}
                     if(panico && izquierda){izquierda();}
                    }
                }    
               }         
            }
         }
}
