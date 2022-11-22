/**
  * Archivo: Lado.java
  * Descripcion: Implementacion de un grafo no dirigido mediante una clase.
  * @author  Albert Diaz 11-10278
  * @author  Nathalia Silvera 12-10921
  * Ultima modificacion: 12/11/2017
  */

public abstract class Lado
{
	//Atributos de la clase abstracta Lado
  	private String id;

  	/**
  	 * Descripcion: Constructor de la clase lado
  	 * Precondicion: id == String
  	 * @param id: identificador del vertice
  	 * Postcondicion: this.id = id
  	 * Orden: O(Constante)
  	 */

  	public Lado(String id) {
  		this.id = id;
  	}

  	/**
  	 * Descripcion: Funcion get del identificador del vertice
  	 * Precondicion: True
  	 * Postcondicion: return id;
  	 * @return id;
  	 * Orden: O(Constante)
  	 */
  	public String getId() {
  		return id;
  	}

  	/**
  	 * Descripcion: Funcion retorna los atributos de la clase 
  	 *              como cadena de caracteres.
  	 * Precondicion: True
  	 * Postcondicion: No implementada
  	 */

  	public abstract String toString();

}