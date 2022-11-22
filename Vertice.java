/**
  * Archivo: Vertice.java
  * Descripcion: Implementacion de un grafo no dirigido mediante una clase.
  * @author  Albert Diaz 11-10278
  * @author  Nathalia Silvera 12-10921
  * Ultima modificacion: 12/11/2017
  */

public class Vertice
{
    //Atributos de la clase Vertice
    private int x;
    private int y;
    private int low;
    private boolean visitado;
    private String posicion;
    private double altura;

    /**
     * Descripcion: Constructor de la clase Vertice
     * Precondicion: posicion == String and x == int and 
     *               y == int and altura == double
     * @param posicion: posicion del vertice
     * @param x: coordenada x de la posicion del vertice
     * @param y: coordenada y de la posicion del vertice
     * @param altura: altura del vertice
     * Postcondicion: this.x = x and  this.y = y and  this.posicion = posicion
     *                this.altura = altura and  visitado = false and low = 0;
     * Orden: O(Constante)
     */

    public Vertice( String posicion, int x, int y, double altura ) {

        this.x = x;
        this.y = y;
        this.posicion = posicion;
        this.altura = altura;
        visitado = false;
        low = 0;
    }

    /** 
     * Descripcion: Retorna el atributo altura de la clase
     * Precondicion: True
     * Postcondicion: return altura;
     * @return altura;
     * Orden: O(Constante)
     */
    public double getAltura() {
        return altura;
    }

    /**
     * Descripcion: Retorna la coordenada x de la posicion del vertice
     * Precondicion: True
     * Postcondicion: return x;
     * @return x;
     * Orden: O(Constante)
     */

    public int getX() 
    {
        return x;
    }

    /**
     * Descripcion: Retorna la coordenada y de la posicion del vertice
     * Precondicion: True
     * Postcondicion: return y;
     * @return y;
     * Orden: O(Constante)
     */

    public int getY() 
    {
        return y;
    }


    /**
     * Descripcion: Funcion set del atributo low
     * Precondicion: True
     * @param l: contador
     * Postcondicion: low = l;
     * Orden: O(Constante)
     */

    public void setLow(int l) 
    {
        low = l;
    }

    /**
     * Descripcion: Retorna el atributo low de la clase
     * Precondicion: True
     * Postcondicion: return low;
     * @return low;
     * Orden: O(Constante)
     */

    public int getLow()
    {
        return low;
    }

    /**
     * Descripcion: Retorna el atributo posicion de la clase
     * Precondicion: True
     * Postcondicion: return posicion;
     * @return posicion;
     * Orden: O(Constante)
     */

    public String getPosicion() 
    {
        return posicion;
    }

    /** 
     * Descripcion: Retorna el atributo visitado de la clase
     * Precondicion True
     * Postcondicion: return visitado;
     * @return visitado;
     * Orden: O(Constante)
     */

    public boolean getVisitado() 
    {
        return visitado;
    }

    /**
     * Descripcion: Funcion set del atributo visitado de la clase
     * Precondicion: t == boolean
     * @param t: vertice visitado
     * Postcondicion: visitado = t;
     * Orden: O(Constante)
     */

    public void setVisitado( boolean t ) 
    {
        visitado = t;
    }

    /** 
     * Descripcion: Funcion retorna los atributos de la clase 
     *              como cadena de caracteres.
     * Precondicion: True
     * Postcondicion: return( "( "+"["+x+","+y+"]" + ", " + altura + " )" );
     * @return ( "( "+"["+x+","+y+"]" + ", " + altura + " )" );
     */
    public String toString() { 
        return( "( "+"["+x+","+y+"]" + ", " + altura + " )" );
    }
}