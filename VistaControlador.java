import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.Timer.*;
import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.Random;

/**
 * Write a description of class VistaControlador here. 
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public class VistaControlador extends JPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Fantasmas blinky, pinky, clyde;
    private Comecocos comecocos;
    private ArrayList <Personajes> personajes;
    private ArrayList <Fantasmas> fantasmas;
    private ArrayList <Casilla> casillas;
    private boolean vida, pausa, panico, exito;
    private Laberinto laberinto;
    private Casilla casilla;
    private Image imagenPausa, imagenMuerte, imagenExito;       //La variable imagen donde irá el fonde de pausa y la de muerte
    private ImageIcon imageIcon;
    private int factor, puntuacion, contadorpanico, contadorcasa, bolas;
    private AudioClip comerbola, comerbolagrande, comerfantasma, muerte;
    //private Random aleatorio;
    
    /**
     * Constructor for objects of class VistaControlador
     */
    
    public VistaControlador(){
        VistaControlador();
    }
    
    public void VistaControlador(){
        //Opciones del JPanel
        setFocusable(true);
        setDoubleBuffered(true);
        setVisible(true);
        setBackground(Color.BLACK);

        //Creamos un timer y añadimos keylistener
        timer = new Timer(125, this);
        timer.start();
        addKeyListener(new TAdapter());
        
        //Creamos el laberinto
        laberinto = new Laberinto();
        bolas = laberinto.totalBolas();
        //Creamos el array para las casillas con los cruces
        casillas = new ArrayList <Casilla>();
        crearCruces();
        // Creamos dos array, uno para introducir todos los personajes que se mueven en el juego y otro solo para los fantasmas.
        personajes = new ArrayList <Personajes>();
        fantasmas = new ArrayList <Fantasmas>();
        
        //Creamos Pacman
        comecocos = new Comecocos();              
        personajes.add(comecocos);             //Se introduce en el Array
        
        //Creamos Blinky
        blinky = new Blinky();
        personajes.add(blinky);
        fantasmas.add(blinky);
        //Creamos Pinky
        pinky = new Pinky();
        personajes.add(pinky);
        fantasmas.add(pinky);
        //Creamos Clyde
        clyde = new Clyde();
        personajes.add(clyde);
        fantasmas.add(clyde);
        
        //Creamos las imagenes de pausa y muerte de pacman
        imageIcon = new ImageIcon(this.getClass().getResource("pausa.jpg"));
        imagenPausa = imageIcon.getImage();
        imageIcon = new ImageIcon(this.getClass().getResource("gameover.jpg"));
        imagenMuerte = imageIcon.getImage();
        imageIcon = new ImageIcon(this.getClass().getResource("exito.jpg"));
        imagenExito = imageIcon.getImage();
        //Inicializamos las variables del juego
        panico=false;
        pausa=false;
        vida=true;
        exito=false;
        factor = 60;
        puntuacion = 0;
        contadorpanico = 40;
        contadorcasa = 20;
        //aleatorio = new Random();
        //Genero los audios
        URL url = VistaControlador.class.getResource("comerbola.wav");
        comerbola = Applet.newAudioClip(url);
        URL url1 = VistaControlador.class.getResource("comerbolagrande.wav");
        comerbolagrande = Applet.newAudioClip(url1);
        URL url2= VistaControlador.class.getResource("comerfantasma.wav");
        comerfantasma = Applet.newAudioClip(url2);
        URL url3 = VistaControlador.class.getResource("muerte.wav");
        muerte = Applet.newAudioClip(url3);
    }

    /**
     * Redefinimos el método paint(g)
     * Primero llamamos al método paint de la superclase.
     * Pintamos el laberinto, los personajes y dos imagenes en caso de pausa o muerte.
     * 
     * 
     */
    public void paint(Graphics g)
    {
       super.paint(g);     
       Graphics2D g2d=(Graphics2D)g;
       for(int x =0; x<laberinto.tamanioLaberintox(); x++){
        for (int y =0; y<laberinto.tamanioLaberintoy(); y++){
           g2d.drawImage(laberinto.getImage(x,y), x*factor, y*factor, this);
         }
        }
       
       for(Personajes figuras: personajes) {
           g2d.drawImage(figuras.getImagen(), figuras.getx(), figuras.gety(), this);
        }

       if(pausa){
           g2d.drawImage(imagenPausa,0,0, this);
        }
       
       if(!vida){
          g2d.drawImage(imagenMuerte,0,0, this);
        }
       
       if(exito){
          g2d.drawImage(imagenExito,0,0, this);
       }
       
       //Pintamos el marcador con la puntuación
       g2d.setFont(new Font("Arial", Font.PLAIN, 54));
       g2d.setColor(Color.WHITE);
       g2d.drawString("Marcador",1020,180);
       g2d.drawString(puntuacion(),1100,240);
       
       //Opciones del método paint()
       Toolkit.getDefaultToolkit().sync();
       setDoubleBuffered(true);
       g2d.dispose();
       repaint();
    }
   
    public void crearCruces(){
        for(int x =0; x< laberinto.tamanioLaberintox(); x++){
          for (int y =0; y< laberinto.tamanioLaberintoy(); y++){
             if(laberinto.getValor(x,y) != 0){
               if( ((laberinto.getValor(x-1,y) != 0) || (laberinto.getValor(x+1,y) != 0)) && ( (laberinto.getValor(x,y-1) !=0) || (laberinto.getValor(x,y+1) !=0) )){
                    casilla = new Casilla(x,y);
                    casillas.add(casilla);
               }                
            }
          }
       }
    }
    
    /**
     * La clase actionPerformed contiene la lógica del juego.
     * Llama a los métodos que mueve a los personajes, comprueban las colisiones.
     * 
     */
    public void actionPerformed(ActionEvent e)
    {
     if(!pausa){
      inteligenciaArtificial();
      for(Personajes personaje: personajes){
        personaje.move();
       }
      //Comprobamos las colisiones
      comprobarColisiones();
     
      //Sistema para comer las bolas pequeñas del laberinto
      if(laberinto.getValor(comecocos.getCasillax(), comecocos.getCasillay()) == 1){
       laberinto.comerBola(comecocos.getCasillax(),comecocos.getCasillay());
       puntuacion = puntuacion+10;
       comerbola.play();
       bolas--;
      }
      //Sistema para comer las bolas de laberinto grandes
      if(laberinto.getValor(comecocos.getCasillax(), comecocos.getCasillay()) == 2){
       laberinto.comerBola(comecocos.getCasillax(),comecocos.getCasillay());
       puntuacion=puntuacion+20;
       contadorpanico = 40;
       comerbolagrande.play();
       panico();
       bolas--;
      }
        
      if(bolas == 0){
         exito=true;
         finPartida();
         removeAll();
        }
        
      if((panico) && (!pausa)){
         contadorpanico--;
        }
        
      if(!pausa){
         contadorcasa--;
        }
            
      if(contadorpanico == 0){
         panicoFin();
        }
        
      if(contadorcasa == 0){
          salirCasa();
        }
     }
    }
    
    public String puntuacion()
    {
        String s = Integer.toString(puntuacion);
        return s;
    }

    public void comprobarColisiones()
    {
     for(Personajes personaje: personajes) {
        //Se recorre el laberinto con sus diferentes valores, si es 0, que corresponde con el muro, se paran los personajes.
        for(int x =0; x< laberinto.tamanioLaberintox(); x++){
          for (int y =0; y< laberinto.tamanioLaberintoy(); y++){         
             if (laberinto.getValor(x,y) == 0){
                Rectangle personajeRect = personaje.crearRectangulo();
                Rectangle muro = new Rectangle(x*60,y*60,60,60);
                if(personajeRect.intersects(muro)){
                   personaje.stop();
                }  
              }
           }
        }
      } 
        
     for(Fantasmas fantasma: fantasmas){              
          Rectangle r1 = fantasma.crearRectangulo();
          Rectangle r2 = comecocos.crearRectangulo();
          if(r1.intersects(r2)){
             if(!panico){
                 muerte.play();
                 finPartida();
               }      
             if(panico){
                 puntuacion = puntuacion+100;
                 comerfantasma.play();
                 muerteFantasma(fantasma);
               } 
            }
        }   
     
     
    }
        
    public void inteligenciaArtificial(){
        Random aleatorio = new Random();
        for(Fantasmas fantasma: fantasmas){ 
           comprobarCruce(fantasma);
           int movimiento = aleatorio.nextInt(4); 
           //Diseñamos la lógica de movimiento de Pinky  
           if(fantasma instanceof Pinky){
             if(!fantasma.muerte && fantasma.cruce){
               if(comecocos.getx() - fantasma.getx() < 0){
                 if(laberinto.getValor(fantasma.getCasillax()-1,fantasma.getCasillay()) ==0){
                     switch (movimiento){
                           case 0:
                           fantasma.arriba();
                           break;
                           case 1:
                           fantasma.abajo();
                           break;
                           case 2:
                           fantasma.arriba();
                           break;
                           case 3:
                           fantasma.abajo();
                           break;
                        }
                 }
                 if(laberinto.getValor(fantasma.getCasillax()-1,fantasma.getCasillay()) !=0){
                     fantasma.izquierda();
                    }
                }
                 
               if(comecocos.getx() - fantasma.getx() > 0){
                 if(laberinto.getValor(fantasma.getCasillax()+1,fantasma.getCasillay()) ==0){   
                     switch (movimiento){
                           case 0:
                           fantasma.arriba();
                           break;
                           case 1:
                           fantasma.abajo();
                           break;
                           case 2:
                           fantasma.arriba();
                           break;
                           case 3:
                           fantasma.abajo();
                           break;
                        }
                 }
                 if(laberinto.getValor(fantasma.getCasillax()+1,fantasma.getCasillay()) !=0){
                     fantasma.derecha();
                    }
                }
                 
               if(comecocos.gety() - fantasma.gety() < 0){
                 if(laberinto.getValor(fantasma.getCasillax(),fantasma.getCasillay()-1) ==0){
                      switch (movimiento){
                           case 0:
                           fantasma.derecha();
                           break;
                           case 1:
                           fantasma.izquierda();
                           break;
                           case 2:
                           fantasma.derecha();
                           break;
                           case 3:
                           fantasma.izquierda();
                           break;
                        }
                    }
                 if(laberinto.getValor(fantasma.getCasillax(),fantasma.getCasillay()-1) !=0){
                     fantasma.arriba();
                    }
                }
                 
               if(comecocos.gety() - fantasma.gety() > 0){
                 if(laberinto.getValor(fantasma.getCasillax(),fantasma.getCasillay()+1) ==0){
                     switch (movimiento){
                           case 0:
                           fantasma.derecha();
                           break;
                           case 1:
                           fantasma.izquierda();
                           break;
                           case 2:
                           fantasma.derecha();
                           break;
                           case 3:
                           fantasma.izquierda();
                           break;
                        }
                    }
                 if(laberinto.getValor(fantasma.getCasillax(),fantasma.getCasillay()+1) !=0){
                     fantasma.abajo();
                    }
                   }
               } 
            }
             
           //Diseñamos la lógica de movimiento de Blinky
           if(fantasma instanceof Blinky){
             if(!fantasma.muerte && fantasma.cruce){
              if(comecocos.gety() - fantasma.gety() < 0){
               if(laberinto.getValor(fantasma.getCasillax(),fantasma.getCasillay()-1) ==0){
                  switch (movimiento){
                      case 0:
                      fantasma.izquierda();
                      break;
                      case 1:
                      fantasma.derecha();
                      break;
                      case 2:
                      fantasma.izquierda();
                      break;
                      case 3:
                      fantasma.derecha();
                      break;
                        }
                    }
               if(laberinto.getValor(fantasma.getCasillax(),fantasma.getCasillay()-1) !=0){
                     fantasma.arriba();
                   }
               }
                 
              if(comecocos.gety() - fantasma.gety() > 0){
               if(laberinto.getValor(fantasma.getCasillax(),fantasma.getCasillay()+1) ==0){
                 switch (movimiento){
                   case 0:
                   fantasma.derecha();
                   break;
                   case 1:
                   fantasma.izquierda();
                   break;
                   }
                }
               if(laberinto.getValor(fantasma.getCasillax(),fantasma.getCasillay()+1) !=0){
                   fantasma.abajo();
                }
              }
              
              if(comecocos.getx() - fantasma.getx() < 0){
               if(laberinto.getValor(fantasma.getCasillax()-1,fantasma.getCasillay()) ==0){
                 switch (movimiento){
                    case 0:
                    fantasma.arriba();
                    break;
                    case 1:
                    fantasma.abajo();
                    break;
                     }
                }
               if(laberinto.getValor(fantasma.getCasillax()-1,fantasma.getCasillay()) !=0){
                    fantasma.izquierda();
                 }
                }
                 
              if(comecocos.getx() - fantasma.getx() > 0){
               if(laberinto.getValor(fantasma.getCasillax()+1,fantasma.getCasillay()) == 0){   
                 switch (movimiento){
                    case 0:
                    fantasma.arriba();
                    break;
                    case 1:
                    fantasma.abajo();
                    break;
                      }
                }
               if(laberinto.getValor(fantasma.getCasillax()+1,fantasma.getCasillay()) !=0){
                     fantasma.derecha();
                 }
               }
              } 
             }
            
           //Diseñamos la lógica de movimiento de Clyde
           if(fantasma instanceof Clyde){
                if(!fantasma.muerte && fantasma.cruce){
                switch(movimiento){
                     case 0:
                     fantasma.arriba();
                     break;
                     case 1:
                     fantasma.abajo();
                     break;
                     case 2:
                     fantasma.derecha();
                     break;
                     case 3:
                     fantasma.izquierda();
                     break;
                    }
               }
           }
        }
        
        //Colisiones entre fantasmas
        Rectangle r3 = blinky.crearRectangulo();
        Rectangle r4 = pinky.crearRectangulo();
        Rectangle r5 = clyde.crearRectangulo();
        if(r3.intersects(r4)){
            blinky.volver();
            pinky.volver();
        }       
        if(r3.intersects(r5)){
            blinky.volver();
            clyde.volver();
        }
        if(r5.intersects(r4)){
            pinky.volver();
            clyde.volver();
        }
     }
    
     public void comprobarCruce(Fantasmas fantasma)
    {
       Rectangle r1 = fantasma.crearRectangulo(); 
       for(Casilla casilla: casillas){
        Rectangle r2 = casilla.crearRectangulo();
        if(r1.contains(r2)){
           fantasma.cruce();
          }
       }              
    }
     
    public void panico()
    {
        panico = true;
        for(Fantasmas fantasma: fantasmas){
             fantasma.panico();
            }     
    }
    
    public void panicoFin()
    {
       panico=false;
       for(Fantasmas fantasma: fantasmas){
             fantasma.finPanico();
            }  
    }
        
    public void finPartida()
    {
      vida = false;
      timer.stop();
    }
 
    public void muerteFantasma(Fantasmas fantasma)
    {
            fantasma.muerte();
            contadorcasa=20;
    }
   
    public void salirCasa(){
        for(Fantasmas fantasma: fantasmas){
            if(fantasma.muerte){
                fantasma.salir();
            }
        }
    }

      public boolean pausa(){
        pausa = !pausa;
        return pausa;
    }
    
    public void nuevoJuego()
    {
        if((exito) || (!vida)){
            VistaControlador();
        }
    }
    
    //Metodos para añadir eventos de teclado
    public void KeyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        switch (code)
        {
            case 38:
                comecocos.arriba();
                comecocos.actualizarimagen("comecocos_up.gif");
                break;
            case 39:  
                comecocos.derecha();
                comecocos.actualizarimagen("comecocos.gif");
                break;
            case 40:
                comecocos.abajo();
                comecocos.actualizarimagen("comecocos_down.gif");
                break;
            case 37:
                comecocos.izquierda();
                comecocos.actualizarimagen("comecocos_left.gif");
                break;
            case 80:
                pausa();
                break;
            case 78:
                nuevoJuego();
                break;
        }
    }
    
     private class TAdapter extends KeyAdapter
    {   
        public void keyPressed(KeyEvent e)
        {
            KeyPressed(e);
        }
    }
}
