/**
  * Archivo: DigrafoActividad1.java
  * Descripcion: Implementacion de un grafo no dirigido mediante una clase.
  * @author  Albert Diaz 11-10278
  * @author  Nathalia Silvera 12-10921
  * Ultima modificacion: 12/11/2017
  */

import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.IOException;


public class DigrafoActividad1 implements Grafo
{

    // Atributos de la clase DigradoActividad1.
    private int nFilas;
    private int nColumnas;
    private int nLados;
    private int nVertices;
    private int[] low;              
    private int time;
    private int mayor;
    private int[][] edificios;
    private String[][] respuesta;
    private Stack<Vertice> pilaVertices = new Stack<Vertice>();
    private ArrayList<Arco> listaDeArcos = new ArrayList<Arco>();
    private ArrayList<ArrayList<Vertice>> componentes = new ArrayList<ArrayList<Vertice>>();
    private ArrayList<ArrayList<Vertice>> listaDeListas = new ArrayList<ArrayList<Vertice>>();

    /** 
     * Descripcion:    Constructor de la clase GrafoNoDirigido.
     * Orden: O(constante)
     */
    
    public void DigrafoActividad1() 
    {
        nFilas = 0;
        nColumnas = 0;
        time = 0;
    }

    /**
     * Descripcion: Funcion que realiza la llamada a las funciones 
     *              para mostrar los desagues
     * Precondicion: dirArchivo == String
     * @param dirArchivo: Archivo de entrada a procesar
     * Postcondicion: Resultado mostrado en pantalla.
     * Orden: O(Cuadratico)
     */

    public void controlProcesado(String dirArchivo) {

        cargarGrafo( dirArchivo );
        crearArcos();
        mayor = mayorAltura();
        tarjan();
        mostrarRespuesta();
    }

    /** 
     * Descripcion:    Carga la informacion almacenada en el archivo.
     * Pre condicion:  dirArchivo == String.
     * @param dirArchivo: nombre del archivo.
     * Post condicion: lectura satisfactoria o una excepcion.
     * @return cargoArchivo
     * Orden: O(cuadratico)
     */
    
    public boolean cargarGrafo(String dirArchivo) 
    {

        boolean cargoArchivo;
        int filas = 0;
        int columnas = 0;
        BufferedReader in = null;
        String s;
        int contador = 0;
        String[] tok;
        int j = 1;
        int k = 0;
        ArrayList<Vertice> lista;
        Vertice v;

        try
        {
            in = new BufferedReader(new FileReader(dirArchivo));

            while((s=in.readLine())!= null)
            {
                tok = s.split(" ");

                if( contador > 1 && contador < nFilas + 2)
                {
                    lista = new ArrayList<Vertice>();
                    for( int i = 0; i < columnas; i++ )
                    {
                        k = i+1;
                        agregarVertice( "["+j+","+k+"]",j, k, 
                                        Double.parseDouble( tok[i] ) );
                    }

                    for( int i = 1; i < columnas + 1; i++ )
                    {
                        edificios[j][i] = Integer.parseInt( tok[i-1]);
                    }

                    j = j+1;
                }

                else if( tok.length == 1 && contador == 0 )
                {
                    filas = Integer.parseInt( tok[0] );
                    setNumeroFilas( filas );
                }

                else if(tok.length == 1 && contador == 1)
                {
                    columnas = Integer.parseInt( tok[0] );
                    setNumeroColumnas( columnas );
                    inicializarEstructuras( filas, columnas );
                }

                else
                {
                    cargoArchivo = false;
                    return cargoArchivo;
                }

                contador = contador + 1;
            }
        }

        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        finally 
        {
            try 
            { 
                if (in != null) 
                {
                    in.close(); 
                }
            }
            catch (Exception e2) 
            {
                e2.printStackTrace();
            }
        }

        cargoArchivo = true;
        return cargoArchivo;
    }

    /**
     * Descripcion: Funcion inicializa la estructura de las matrices
     * Precondicion: filas == int and columnas == int
     * @param filas: numero de filas
     * @param columnas: numero de columnas
     * Postcondicion: Matrices inicializadas 
     * Orden: O(Lineal)
     */

    public void inicializarEstructuras( int filas, int columnas ) 
    {

        edificios = new int[filas + 2][columnas + 2];
        respuesta = new String[filas][columnas];

        for (int i = 0, j = 0; i < respuesta.length;) 
        {

            respuesta[i][j] = "0";
            if (j == respuesta[0].length - 1) 
            {
                i++;
                j = 0;
            }

            else 
            {
                j++;
            }
 
        }

        for (int i = 1, j = 1; i < edificios.length-1;) 
        {

            edificios[i][j] = 0;  
 
            if (j == edificios[0].length - 2) 
            {
                i++;
                j = 0;
            }

            else 
            {
                j++;
            }
 
        }

        for( int i = 0; i < columnas + 2; i++ )
        {
            edificios[0][i] = -1;
            edificios[filas+1][i] = -1;
        }

        for( int i = 0; i < filas + 2; i++ )
        {
            edificios[i][0] = -1;
            edificios[i][columnas + 1] = -1;
        }
    }

    /**
     * Descripcion: Funcion que crea los arcos del grafo
     * Precondicion: True
     * Postcondicion: Arco agregado al grafo.
     * Orden: O(Cuadraticos)
     */

    public void crearArcos() 
    {

        Vertice v1;
        Vertice v2;
        int k = 0;

        for( int i = 1; i < nFilas+1; i++ )
        {
            for( int j = 1; j < nColumnas+1; j++ )
            {
                if( edificios[i][j] >= edificios[i][j-1] && edificios[i][j-1] != -1 )
                {
                    k = j-1;
                    v1 = obtenerVertice("["+i+","+j+"]");
                    v2 = obtenerVertice("["+i+","+k+"]");
                    agregarArco("("+v1.getPosicion()+","+v2.getPosicion()+")",v1,v2);
                }

                if( edificios[i][j] >= edificios[i][j+1] && edificios[i][j+1] != -1 )
                {
                    k = j+1;
                    v1 = obtenerVertice("["+i+","+j+"]");
                    v2 = obtenerVertice("["+i+","+k+"]");
                    agregarArco("("+v1.getPosicion()+","+v2.getPosicion()+")",v1,v2);
                }

                if( edificios[i][j] >= edificios[i+1][j] && edificios[i+1][j] != -1 )
                {
                    k = i+1;
                    v1 = obtenerVertice("["+i+","+j+"]");
                    v2 = obtenerVertice("["+k+","+j+"]");
                    agregarArco("("+v1.getPosicion()+","+v2.getPosicion()+")",v1,v2);
                }

                if( edificios[i][j] >= edificios[i-1][j] && edificios[i-1][j] != -1 )
                {
                    k = i-1;
                    v1 = obtenerVertice("["+i+","+j+"]");
                    v2 = obtenerVertice("["+k+","+j+"]");
                    agregarArco("("+v1.getPosicion()+","+v2.getPosicion()+")",v1,v2);
                }
            }
        }
    }
    
    /**
     * Descripcion: Funcion que retorna el numero de filas
     * Precondicion: True
     * Postcondicion: return nFilas
     * @return nFilas
     * Orden: O(Constante)
     */

    public int numeroDeFilas() 
    {
        return nFilas;
    }

    /**
     * Descripcion: Funcion set que obtiene el numero de filas
     * Precondicion: filas == int
     * @param filas: numero filas de la matriz 
     * Postcondicion: nFilas = filas
     * Orden: O(Constante)
     */
    
    public void setNumeroFilas(int filas) 
    {
        nFilas = filas;
    }

    /**
     * Descripcion: Funcion que retorna el numero de columnas
     * Precondicion: True
     * Postcondicion: return nColumnas
     * @return nColumnas
     * Orden: O(Constante)
     */

    public int numeroDeColumnas() 
    {
        return nColumnas;
    }

    /**
     * Descripcion: Funcion set que obtiene el numero de columnas
     * Precondicion: columnas == int
     * @param columnas: numero columnas de la matriz 
     * Postcondicion: nColumnas = columnas
     * Orden: O(Constante)
     */

    public void setNumeroColumnas(int columnas) 
    {
        nColumnas = columnas;
    }

    /**
     * Descripcion: Funcion que retorna un booleano si el vertice 
     *              es agregado al grafo
     * Precondicion: v == Vertice
     * @param v: Vertice a agregar 
     * Postcondicion: return true or false
     * @return true or false
     * Orden: 0(Constante)
     */

    public boolean agregarVertice(Vertice v) 
    {
        ArrayList<Vertice> listaDeVertices = new ArrayList<Vertice>();

        if( listaDeVertices.add( v ) && listaDeListas.add( listaDeVertices ) )
        {
            return true;
        }
        
        return false;
    }

    /**
     * Descripcion: Funcion que retorna si los vertices son agregados al grafo. 
     * Precondicion: posicion == String and px == int and 
     *               py == int and altura == double
     * @param posicion: posicion del vertice.
     * @param px: coordenada x de la posicion del vertice.
     * @param py: coordenada y de la posicion del vertice.
     * @param altura: altura del vertice.
     * Postcondicion: Vertice agregado
     * @return true or false
     * Orden: O(Constante)
     */

    public boolean agregarVertice( String posicion, int px, int py, double altura ) 
    {

        Vertice v = new Vertice( posicion, px, py, altura );

        if( agregarVertice( v ) )
        {
            return true;
        }

        return false;
    }

    /**
     * Descripcion:  Funcion para obtener los vertices del grafo
     * Precondicion: posicion == String
     * @param posicion: posicion del vertice a obtener
     * Postcondicion: retorna el vertice 
     * @return v;
     * Orden: O(Lineal)
     */
    
    public Vertice obtenerVertice( String posicion ) {

        ArrayList<Vertice> listaDeVertices = vertices();

        for( Vertice v: listaDeVertices )
        {
            if( v.getPosicion().equals(posicion) )
            {
                return v;
            }
        }
        throw new NoSuchElementException("El vertice buscado no existe en el grafo.");
    }

    /** 
     * Descripcion:    Retorna una lista de vertices del grafo.
     * Pre condicion:  True
     * Post condicion: return lista
     * @return lista
     * Orden: O(cuadratico)
     */
    
    public ArrayList<Vertice> vertices() {

        ArrayList<Vertice> lista = new ArrayList<Vertice>();

        for( ArrayList<Vertice> list: listaDeListas )
        {
            for( Vertice ver: list )
            {
                    lista.add( ver );
                    break;
            }
        }
        return lista;
    }

    /**
     * Descripcion: Funcion que calcula los sucesores del vertice
     * Precondicion: posicion == String
     * @param posicion: posicion del vertice a calcular sus sucesores
     * Postcondicion: lista de sucesores
     * @return listaDeSucesores;
     * Orden: O(Lineal)
     */
 
    public ArrayList<Vertice> sucesores(String posicion) {

        Vertice v;
        Vertice v1;
        Vertice v2;
        ArrayList<Vertice> listaSucesores = new ArrayList<Vertice>();
        ArrayList<Vertice> listaAux = new ArrayList<Vertice>();
        ArrayList<Vertice> listaVertices = vertices();

        v = obtenerVertice( posicion );

        for( int i = 0; i < listaDeListas.size(); i++ )
        {
            if( listaDeListas.get(i).get(0).getPosicion().equals(posicion))
            {
                listaAux = listaDeListas.get(i);
                break;
            }
        }

        for( int j = 1; j < listaAux.size(); j++ )
        {
            listaSucesores.add(listaAux.get(j));
        }

        return listaSucesores;
    }

    /**
     * Descripcion: Funcion que calcula las componentes fuertemente 
     *              conexas del grafo.
     * Precondicion: True
     * Postcondicion: Lista de componentes fuertemente conexas del grafo
     * Orden: O(Cuadratico)
     */

    public void tarjan() {

        int time = 0;
        ArrayList<Vertice> lista = vertices();

        for ( Vertice v: lista ) 
        {
            if (!v.getVisitado())
            {
                dfs(v);
            }
        }
    }

    /**
     * Descripcion: Funcion que utiliza el agoritmo dfs, para hacer el 
     *              recorrido y buscar las componentes conexas del grafo.
     * Precondicion: v == Vertice
     * @param v: Vertice del grafo 
     * Postcondicion: Lista de componentes fuermente conexas
     * Orden: O(Lineal)
     */

    private void dfs(Vertice v) { 

        ArrayList<Vertice> sucesor = sucesores(v.getPosicion());

        v.setLow(time++);
        v.setVisitado(true);

        pilaVertices.push(v);

        boolean componenteRaiz = true;

        for ( Vertice s: sucesor ) 
        {
            if (!s.getVisitado())
            {
                dfs(s);
            }

            if ( v.getLow() > s.getLow() ) 
            {
                v.setLow(s.getLow());
                componenteRaiz = false;
            }
        }

        if( componenteRaiz ) 
        {
            ArrayList<Vertice> comp = new ArrayList<Vertice>();
            while(true)
            {
                Vertice x = pilaVertices.pop();
                comp.add(x);

                x.setLow(Integer.MAX_VALUE);
                if(x.getPosicion()  == v.getPosicion())
                    break;
            }

            calculaDesagues(comp);
            componentes.add(comp);
        }

    }

    /**
     * Descripcion: Funcion que calcula los desagues del grafo. 
     * Precondicion: lista == ArrayList of Vertice
     * @param lista: lista de componentes fuertemente conexas del grafo.
     * Postcondicion: Desagues calculados.
     * Orden: O(Lineal)
     */

    public void calculaDesagues(ArrayList<Vertice> lista) {

        ArrayList<Double> adya =  new ArrayList<Double>();
        double minMax = mayor;
        boolean control = false;
        double altura = lista.get(0).getAltura();

        for( Vertice v: lista )
        {
            adya.add( (double) (edificios[v.getX()][v.getY()+1]));

            adya.add( (double) edificios[v.getX()][v.getY()-1]);

            adya.add( (double) edificios[v.getX()+1][v.getY()]);

            adya.add( (double) edificios[v.getX()-1][v.getY()]);
        }

        for( int i = 0; i < adya.size(); i++ )
        {
            if( altura > adya.get(i) )
            {
                control = true;
                break;
            }
        }

        if( !control )
        {
            for( Vertice v: lista )
            {
                respuesta[v.getX()-1][v.getY()-1] = "x";
            }

            for( int j = 0; j < adya.size(); j++ )
            {
                if( altura < adya.get(j) && adya.get(j) < minMax )
                {
                    minMax = adya.get(j);
                }
            }
        }

    }

    /** 
     * Descripcion: Calcula el vertice con mayor altura del grafo
     * Precondicion: True
     * Postcondicion: retorna la mayor altura
     * @return mayor;
     * Orden(Cuadratico)
     */ 

    public int mayorAltura() {

        int mayor = 0;

        for( int i = 1; i < nFilas + 1; i++ )
        {
            for( int j = 1; j < nColumnas + 1; j++)
            {
                if( edificios[i][j] > mayor )
                {
                    mayor = edificios[i][j];
                }
            }
        }

        return mayor;
    }

    /** 
     * Descripcion:    Agrega un nuevo arco al grafo.
     * Pre condicion:  a == Arco.
     * @param          a: arco a insertar.
     * @return         true si la insercion se lleva a cabo,
     *                 false en caso contrario.
     * Orden: O(Constante)
     */
    
    public boolean agregarArco(Arco a) {

        if( listaDeArcos.add( a ) )
        {
            return true;
        }

        return false;
    } 

    /** 
     * Descripcion:   Agrega un nuevo arco al grafo.
     * Pre condicion: posicion == String and 
     *                extremoInicial == String and 
     *                extremoFinal == String
     * @param posicion: identificador del arco.
     * @param extremoInicial: extremo inicial del arco.
     * @param extremoFinal: extremo final del arco.
     * @return true or false.
     * Orden: O(Constante)
     */
    
    public boolean agregarArco(String posicion, 
                               Vertice extremoInicial,
                               Vertice extremoFinal ) {

        Arco arc = new Arco( posicion, extremoInicial, extremoFinal );

        if( agregarArco( arc ) )
        {
            agregarIncidencia( arc );
            return true;
        }

        return false;
    }

    /**
     * Descripcion:    Obtiene la arco de la posicion dada.
     * Pre condicion:  posicion == String
     * @param  posicion: Posicion del arco.
     * @return arista; y  una excepcion en caso de no existir.
     * @exception NoSuchElementException
     *"El arco buscada no existe en el grafo."
     * Orden: O(Lineal)
     */
    
    public Arco obtenerArco(String posicion) {

        for( Arco arco: listaDeArcos )
        {
            if( arco.getId().equals(posicion) )
                return arco;
        }
        throw new NoSuchElementException("La Arco buscado " + 
                                       "no existe en el grafo.");
    }

    /**
     * Descripcion: Agrega la incidencia del arco a
     * Precondicion: a == Arco
     * @param a = Arco del grafo
     * Postcondicion: Incidencia del arco agregada
     * Orden: O(Lineal)
     */

    public void agregarIncidencia( Arco a )
    {
        int i = 0;
        for( i = 0; i < listaDeListas.size(); i++ )
        {
            if( listaDeListas.get(i).get(0).getPosicion().equals( a.getExtremoInicial().getPosicion() ) )
            {
                listaDeListas.get(i).add( a.getExtremoFinal() );
            }
        }
    }

    /**
     * Descripcion: Funcion adyacentes de la interfaz
     * Precondicion: posicion == String
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */

    public ArrayList<Vertice> adyacentes(String posicion) {

          throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Funcion agregarVertice de la interfaz
     * Precondicion: posicion == String and altura == double
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */


    public boolean agregarVertice( String posicion, double altura ) {

        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Lista de arreglos de lados de la interfaz
     * Precondicion: True
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */


    public ArrayList<Lado> lados() {

        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /** 
     * Descripcion: Calcula el grado del grafo
     * Precondicion: True
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */

    public int grado(String id)
    {
        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Funcion estaLado de la interfaz
     * Precondicion: u == String and v == String
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */


    public boolean estaLado(String u, String v){

        throw new UnsupportedOperationException("Este metodo no ha sido implementado");

    }

    /**
     * Descripcion: Funcion incidentes de la interfaz
     * Precondicion: posicion == String
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */


    public ArrayList<Lado> incidentes(String posicion) {

        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Funcion que clona el grafo de la interfaz
     * Precondicion: True
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */
    
    public Object clone() {

        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    } 

    /**
     * Descripcion: Funcion EliminarVertice de la interfaz
     * Precondicion: id == String
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */


    public boolean eliminarVertice(String id) {
    
        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Funcion estaVertice de la interfaz
     * Precondicion: posicion == String
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */

    public boolean estaVertice( String posicion ) {

        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Funcion toString de la interfaz
     * Precondicion: True
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */

    
    public String toString() {

        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Funcion de la interfaz que obtiene el numero de vertices 
     * Precondicion: True
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */

    public int numeroDeVertices() 
    {
        
        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Funcion de la interfaz que obtiene el numero de Lados 
     * Precondicion: Tru
     * Postcondicion: No es implementada
     * @exception UnsupportedOperationException 
     * "Este metodo no ha sido implementado"
     * Orden: O(Constante)
     */


    public int numeroDeLados() 
    {
        
        throw new UnsupportedOperationException("Este metodo no ha sido implementado");
    }

    /**
     * Descripcion: Funcion que muestra la matriz con los desagues en consola
     * Precondicion: True
     * Postcondicion: matriz de desagues mostrado en consola
     * Orden: 0(cuadratico)
     * Orden: O(Constante)
     */ 

    public void mostrarRespuesta() {

        System.out.println( "Matriz de respuestas" );
        for( int i = 0; i < nFilas; i++ )
        {
            for( int j = 0; j < nColumnas; j++ )
            {
                System.out.print( respuesta[i][j] + " " );
            }

            System.out.println();
        }

        System.out.println();
    }

} // Fin de la clase DigrafoActividad1