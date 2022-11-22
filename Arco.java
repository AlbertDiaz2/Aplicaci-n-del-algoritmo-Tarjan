/**
  * Archivo: Arco.java
  * Descripcion: Implementacion de un grafo no dirigido mediante una clase.
  * @author  Albert Diaz 11-10278
  * @author  Nathalia Silvera 12-10921
  * Ultima modificacion: 12/11/2017
  */


public class Arco extends Lado
{
    // Atributos de la clase Arco 
    private Vertice extremoInicial;
    private Vertice extremoFinal;

    /** 
     * Descripcion: Constructor de la clase Arco
     * Precondicion: id == String and extremoInicial == Vertice
     *               and extremoFinal == Vertice
     * @param id: identificador del Vertice
     * @param extremoInicial: extremo inicial del vertice
     * @param extremoFinal: extremo final del vertice
     * Postcondicion: super(id); and this.extremoInicial = extremoInicial;
     *                and this.extremoFinal = extremoFinal;
     * Orden: O(Constante)
     */
  
    public Arco(String id, Vertice extremoInicial, Vertice extremoFinal) {
        super( id );
        this.extremoInicial = extremoInicial;
        this.extremoFinal = extremoFinal;
    }

    /**
     * Descripcion: Funcion get del extremo inicial del vertice
     * Precondicion: True
     * Postcondicion: return extremoInicial;
     * @return extremoInicial;
     * Orden: O(Constante)
     */

    public Vertice getExtremoInicial() {
        return extremoInicial;
    }

    /**
     * Descripcion: Funcion get del extremo final del vertice
     * Precondicion: True
     * Postcondicion: return extremoFinal;
     * @return extremoFinal;
     * Orden: O(Constante)
     */

    public Vertice getExtremoFinal() {
        return extremoFinal;
    }

    /**
     * Descripcion: Retorna el extremoInicial y Final del vertice 
     *              como cadena de caracteres.
     * Precondicion: True
     * Postcondicion: return( "( " + extremoInicial + ", " + extremoFinal + " )" );
     * @return( "( " + extremoInicial + ", " + extremoFinal + " )" );
     * Orden: O(Constante)
     */

    public String toString() {
        return( "( " + extremoInicial + ", " + extremoFinal + " )" );
    }
}