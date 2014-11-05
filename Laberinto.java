import javax.swing.ImageIcon;
import java.awt.Image;
/**
 * @author Aitor Alcorta
 * @version 1.0.15052014
 */
class Laberinto
{
    private int [][] matriz = 
            {{0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,2,1,1,1,1,1,1,1,1,1,2,0},
            {0,1,0,0,1,0,0,0,0,1,0,1,0},
            {0,1,0,1,1,1,1,1,1,1,1,1,0},
            {0,1,1,1,0,0,0,1,0,1,0,1,0},
            {0,1,0,1,0,3,0,1,0,1,0,1,0},
            {0,1,1,1,0,3,0,1,0,1,0,1,0},
            {0,1,0,1,0,3,0,1,0,1,0,3,0},
            {0,1,1,1,0,3,0,1,0,1,0,1,0},
            {0,1,0,1,0,3,0,1,0,1,0,1,0},
            {0,1,1,1,0,0,0,1,0,1,0,1,0},
            {0,1,0,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,1,0,0,0,0,1,0,1,0},
            {0,2,1,1,1,1,1,1,1,1,1,2,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0}};   
            
    private Image imagen;       //La variable imagen donde irá la imagen de la figura, ya sea muro, punto pequeño, punto grando o punto vacío.
    private ImageIcon imageIcon;
    private int bolas;
    //private boolean arriba, abajo, izquierda, derecha;
    
    Laberinto() 
       {      
           bolas = 0;
        }

    //Metodo que permite recuperar la matriz
    public Image getImage(int x, int y) 
    {
        int localizacion = matriz[x][y];
            if(localizacion == 0)
            {
                imageIcon = new ImageIcon(this.getClass().getResource("pared.gif"));
                imagen = imageIcon.getImage();
                return imagen;
            }
            
            if(localizacion == 1)
            {
                imageIcon = new ImageIcon(this.getClass().getResource("puntopeque.gif"));
                imagen = imageIcon.getImage();
                return imagen;
            }
            
            if(localizacion == 2)
            {
                imageIcon = new ImageIcon(this.getClass().getResource("puntogran.gif"));
                imagen = imageIcon.getImage();
                return imagen;
            }
            
            if(localizacion == 3)
            {
                imageIcon = new ImageIcon(this.getClass().getResource("vacio.gif"));
                imagen = imageIcon.getImage();
                return imagen;
            }
            
            else
            {
                return null;  
            }
    }
    
    public int getValor(int x, int y)
    {
        return matriz[x][y];
    }

    public void comerBola( int x, int y)
    {
          matriz[x][y] = 3;
    }
    
    public int tamanioLaberintox()
    {
        return matriz.length;
    }
    
    public int tamanioLaberintoy()
    {
        return matriz[0].length;
    }
    
    public int totalBolas(){
        for(int x =0; x<tamanioLaberintox(); x++){
         for (int y =0; y<tamanioLaberintoy(); y++){
           int valor = getValor(x,y);
           if((valor == 1) || (valor == 2)){
             bolas++;
            }
          }
        }
        return bolas;
    }
    
    public boolean arriba(int x, int y){
        if(matriz[x][y-1] == 0){
            return false;
        }
        else{
            return true;}
    }
    
    public boolean abajo(int x, int y){
        if(matriz[x][y+1] == 0){
            return false;
        }
        else{
            return true;}
    }
    
    public boolean derecha(int x, int y){
        if(matriz[x+1][y] == 0){
            return false;
        }
        else{
            return true;}
    }
    
    public boolean izquierda(int x, int y){
        if(matriz[x-1][y] == 0){
            return false;
        }
        else{
            return true;}
    }
}
    
