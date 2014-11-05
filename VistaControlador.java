import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
/**
 * Write a description of class VistaControlador here. 
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
public class VistaControlador extends JPanel implements ActionListener
{
    private Timer timer;
    private Personajes blinky, pinky, clyde ;
    private Comecocos comecocos;
    private ArrayList <Personajes> personajes;
    private ArrayList <Cruces> cruces;
    private boolean vida, pausa, panico, exito;
    private Laberinto laberinto;
    private int puntuacion, contadorpanico, contadorcasa, bolas;
    private AudioClip comerbola, comerbolagrande, comerfantasma, muerte;
    /**
     * Constructor vacío
     */
    public VistaControlador(){
        nuevoJuego();
    }
    
    /**
     * Método al que llama el constructor
     */
    public void nuevoJuego(){
        //Opciones del JPanel
        setFocusable(true);
        setDoubleBuffered(true);
        setVisible(true);
        setBackground(Color.BLACK);
        //Creamos un timer y añadimos keylistener
        timer = new Timer(150, this);
        timer.start();
        addKeyListener(new TAdapter());
        //Creamos el laberinto
        laberinto = new Laberinto();
        bolas = laberinto.totalBolas();
        //Creamos el array para las casillas con los cruces
        cruces = new ArrayList <Cruces>();
        crearCruces();
        // Creamos el array para introducir todos los personajes que se mueven en el juego.
        personajes = new ArrayList <Personajes>();
        //Creamos Pacman
        comecocos = new Comecocos();              
        personajes.add(comecocos);
        //Creamos Blinky
        blinky = new Blinky();
        personajes.add(blinky);
        //Creamos Pinky
        pinky = new Pinky();
        personajes.add(pinky);
        //Creamos Clyde
        clyde = new Clyde();
        personajes.add(clyde);
        //Inicializamos las variables del juego
        panico=false;
        pausa=false;
        vida=true;
        exito=false;
        puntuacion = 0;
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
     * @param objeto tipo Graphics.
     * @Override Redefine el método paint
     */
    public void paint(Graphics g)
    {
       super.paint(g);     
       Graphics2D g2d=(Graphics2D)g;
       for(int x =0; x<laberinto.tamanioLaberintox(); x++){
        for (int y =0; y<laberinto.tamanioLaberintoy(); y++){
           g2d.drawImage(laberinto.getImage(x,y), x*60, y*60, this);
         }
        }
       
       for(Personajes figuras: personajes) {
           g2d.drawImage(figuras.getImagen(), figuras.getx(), figuras.gety(), this);
        }

       if(pausa){
           ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("pausa.jpg"));
           Image imagenPausa = imageIcon.getImage();
           g2d.drawImage(imagenPausa,0,0, this);
        }
       
       if(!vida){
           ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("gameover.jpg"));
           Image imagenMuerte = imageIcon.getImage();
           g2d.drawImage(imagenMuerte,0,0, this);
        }
       
       if(exito){
           ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("exito.jpg"));
           Image imagenExito = imageIcon.getImage();
           g2d.drawImage(imagenExito,0,0, this);
        }
       //Pintamos el marcador con la puntuación
       g2d.setFont(new Font("Arial", Font.PLAIN, 54));
       g2d.setColor(Color.WHITE);
       g2d.drawString("Marcador",1020,180);
       g2d.drawString("Puntos: "+puntuacion,1020,240);
       if(panico){
           int tiempo = contadorpanico*(125)/1000;
           g2d.drawString("Tiempo: "+tiempo,1020,300);
        }
       //Opciones del método paint()
       Toolkit.getDefaultToolkit().sync();
       setDoubleBuffered(true);
       g2d.dispose();
       repaint();
    }
   
    /**
     * Metodo que comprueba los cruces y codos de un laberinto y los guarda en un array
     * 
     */
    public void crearCruces(){
        for(int x =0; x< laberinto.tamanioLaberintox(); x++){
          for (int y =0; y< laberinto.tamanioLaberintoy(); y++){
             if(laberinto.getValor(x,y) != 0){
               if( (laberinto.izquierda(x,y) || laberinto.derecha(x,y)) && ( laberinto.abajo(x,y) || laberinto.arriba(x,y) )){
                    Cruces cruce = new Cruces(x,y);
                    cruces.add(cruce);
               }                
            }
          }
       }
    }
    
    /**
     * Contiene la lógica del juego.
     * Llama a los métodos que mueve a los personajes, comprueban las colisiones.
     * @param objeto tipo ActionEvent. 
     */
    public void actionPerformed(ActionEvent e){
     if(!pausa){
      for(Personajes personaje: personajes){
        comprobarDirecciones(personaje);
        comprobarCruce(personaje);
        if(personaje instanceof Fantasmas){
            Fantasmas fantasma = (Fantasmas) personaje;
            fantasma.inteligenciaArtificial(comecocos.getx(),comecocos.gety());
        }
        personaje.move();
       }
      comprobarColisiones();//Método que comprueba las diferentes colisiones del juego y toma una decición
      comerBolas();//Método que gestiona la ingesta de píldoras por parte de Comecocos
      //Método que gestiona el fin de partida cuando Comecocos acaba con todas las píldoras
      if(bolas == 0){
         exito=true;
         finPartida();
         removeAll();
        }
      //Contador del pánico
      if((panico) && (!pausa)){
         contadorpanico--;
        }
      //Contador de los fantasmas en casa
      if(!pausa){
         contadorcasa--;
        } 
      //Llamada al método panicoFin() cuando el contador llega a 0
      if(contadorpanico == 0){
         panicoFin();
        }
      //Llamada al método salirCasa(( cuando el contador llega a 0
      if(contadorcasa == 0){
          salirCasa();
        }
     }
    }
    
    /**
     * Comecocos come las píldoras del juego
     * 
     */
    public void comerBolas(){
      //Sistema para comer las bolas de laberinto pequeñas
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
    }
    
     /**
     * Metodo que comprueba las diferentes colisiones de los personajes
     * 
     */
    public void comprobarColisiones()
    {
      for(Personajes personaje: personajes){
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
         //Colisiones entre fantasmas
         if(personaje instanceof Fantasmas){
             Rectangle blinkyRect = personaje.crearRectangulo();
             Rectangle pinkyRect = personaje.crearRectangulo();
             Rectangle clydeRect = personaje.crearRectangulo();
             if(blinkyRect.intersects(pinkyRect)){
               blinky.volver();
               pinky.volver();
            }     
            if(blinkyRect.intersects(clydeRect)){
                blinky.volver();
                clyde.volver();
            }
            if(clydeRect.intersects(pinkyRect)){
                pinky.volver();
                clyde.volver();
            } 
         }
        
        }
        //Sistema de colision entre Comecocos y los fantasmas
        if(personaje instanceof Fantasmas){              
          Rectangle fantasmaRect = personaje.crearRectangulo();
          Rectangle comecocosRect = comecocos.crearRectangulo();
          if(fantasmaRect.intersects(comecocosRect)){
             int vidas = comecocos.vidasComecocos();
             if(!panico){
                 muerte.play();
                 comecocos.matarComecocos();
                 if(vidas == 0) {
                     finPartida();
                    }
               }      
             if(panico){
                 puntuacion = puntuacion+100;
                 comerfantasma.play();
                 Fantasmas fantasma = (Fantasmas)personaje; //Cast al personaje para declararlo de la clase Fantasmas
                 muerteFantasma(fantasma);
               } 
            }
        }
     }    
    }
    
    /**
     * Metodo que comprueba si un fantasma se encuentra en un cruce
     * @param objeto tipo Personajes.
     */
    public void comprobarCruce(Personajes personaje)
    {
       Rectangle r1 = personaje.crearRectangulo(); 
       for(Cruces cruce: cruces){
        Rectangle r2 = cruce.crearRectangulo();
        if(r1.contains(r2)){
           personaje.cruce();
          }
       }
     
    }
    
    /**
     * Metodo que comprueba las direcciones libres de los personajes
     * @param objeto tipo Personajes.
     */
    public void comprobarDirecciones(Personajes personaje)
    {
        int x = personaje.getCasillax();
        int y = personaje.getCasillay();
        personaje.direccionesLibres(laberinto.arriba(x,y), laberinto.abajo(x,y), laberinto.derecha(x,y), laberinto.izquierda(x,y));
    }
    
    /**
     * Metodo que inicia modo pánico
     */
    public void panico()
    {
        panico = true;
        for(Personajes personaje: personajes){
            if(personaje instanceof Fantasmas){
                Fantasmas fantasma = (Fantasmas) personaje;
                fantasma.panico();
            }
        }     
    }
    
    /**
     * Metodo que finaliza modo pánico
     */
    public void panicoFin()
    {
       panico=false;
       for(Personajes personaje: personajes){
            if(personaje instanceof Fantasmas){
                Fantasmas fantasma = (Fantasmas) personaje;
                fantasma.finPanico();
            }
        }  
    }
      
    /**
     * Metodo que gestiona la muerte de los fantasmas
     * @param objeto tipo Fantasmas
     */
    public void muerteFantasma(Fantasmas fantasma)
    {
        fantasma.muerte();
        contadorcasa=20;
    }
   
    /**
     * Metodo que gestiona la salida de los fantasmas de casa
     */
    public void salirCasa(){
        for(Personajes personaje: personajes){
            if(personaje instanceof Fantasmas){
                Fantasmas fantasma = (Fantasmas) personaje;
                 if(fantasma.muerte){
                  fantasma.salir();
                }
            }
        }  
    }
   
    /**
     * Metodo que gestiona la pausa del juego
     * @return tipo booleano pausa
     */
    public boolean pausa(){
        pausa = !pausa;
        return pausa;
    }
    
     /**
     * Metodo que crea un nuevo juego
     */
    public void regenerarJuego()
    {
        if((exito) || (!vida)){
            nuevoJuego();
        }
    }
    
    /**
     * Metodo que gestiona el fin de partida
     */
    public void finPartida()
    {
      vida = false;
      removeAll();
      timer.stop();
    }
    
    public void pulsarTecla(KeyEvent e)
    {
        int code = e.getKeyCode();
        switch (code)
        {
            case 38:
                 comecocos.moverArriba();
                 break;
            case 39:  
                 comecocos.moverDerecha();
                 break;
            case 40:
                 comecocos.moverAbajo();
                 break;    
            case 37:
                 comecocos.moverIzquierda();
                 break;
            case 80:
                 pausa();
                 break;
            case 78:
                 regenerarJuego();
                 break;
        }
    }
    
     private class TAdapter extends KeyAdapter
    {   
        public void keyPressed(KeyEvent e)
        {
            pulsarTecla(e);
        }
    }
}
