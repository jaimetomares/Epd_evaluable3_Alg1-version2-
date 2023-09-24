package epd_evaluable_iii;

import java.io.*;
import poo.io.IO;

//Made by Víctor Jesús Reina López & Jaime Baquerizo Delgado
public class EPD_evaluable_III {

    static LinkedStack<Vertice> calls = new LinkedStack<>();
    static boolean end = false;

    public static void main(String[] args) {
        //Abrimos y guardamos en una variable String el fichero con las ciudades
        String fichero1 = "/Users/user/Desktop/Victor/GIISI/2ºGIISI/1ºCuatrimestre/Algoritmica I/EPD/EPD 8 Evaluable 2/Proyecto/data/berlin52.tsp";
        String fichero2 = "/Users/user/Desktop/Victor/GIISI/2ºGIISI/1ºCuatrimestre/Algoritmica I/EPD/EPD 8 Evaluable 2/Proyecto/data/kroA100.tsp";
        String fichero3 = "/Users/user/Desktop/Victor/GIISI/2ºGIISI/1ºCuatrimestre/Algoritmica I/EPD/EPD 8 Evaluable 2/Proyecto/data/kroA150.tsp";
        String fichero4 = "/Users/user/Desktop/Victor/GIISI/2ºGIISI/1ºCuatrimestre/Algoritmica I/EPD/EPD 8 Evaluable 2/Proyecto/data/kroA200.tsp";
        String fichero5 = "/Users/user/Desktop/Victor/GIISI/2ºGIISI/1ºCuatrimestre/Algoritmica I/EPD/EPD 8 Evaluable 2/Proyecto/data/usa13509.tsp";
        String fichero6 = "/Users/user/Desktop/Victor/GIISI/2ºGIISI/1ºCuatrimestre/Algoritmica I/EPD/EPD 8 Evaluable 2/Proyecto/data/vm1084.tsp";
        String fichero7 = "/Users/user/Desktop/Victor/GIISI/2ºGIISI/1ºCuatrimestre/Algoritmica I/EPD/EPD 8 Evaluable 2/Proyecto/data/vm1748.tsp";
        String fichero8 = "/Users/user/Desktop/Victor/GIISI/2ºGIISI/1ºCuatrimestre/Algoritmica I/EPD/EPD 8 Evaluable 2/Proyecto/data/a280.tsp";

        //Creamos la matriz donde guardaremos todas las mDistancias
        double[][] mDistancias = Ficheros(fichero2);
        //Creamos el grafos que nos ayudará resolver el problema TSP
        //Así que lo creamos en función de la matriz mDistancias
        Grafo graph = new Grafo(mDistancias.length);
        //Y creamos el nñumero de vértices necesarios
        for (int i = 0; i < graph.tablAdc.length; i++) {
            graph.nuevoVertice(i);
        }
        //Seguido de la creacion de las aristas de nuestro grafo necesarias
        for (int j = 0; j < graph.size(); j++) {
            for (int x = 0; x < graph.size(); x++) {
                graph.nuevaArista(j, x);
            }

        }
        int mejorCamino[] = new int[graph.numVerts + 1];
        System.out.println("Especifique en que ciudad desea que empieze nuestra búsqueda:\n");
        int ciudad = (int) IO.readNumber();
        System.out.println("Calculando el camino...\n");
        backtracking(graph.getVertice(ciudad - 1), graph, mejorCamino, mDistancias);
        System.out.println("Camino calulado:\n");
        System.out.println(ImprimirCamino(mejorCamino, mDistancias));

        //Creamos variables auxiliares necesarias para nuestra el cáculo de los tiempos de ejecución de nuestros métodos
        long start;
        long stop;
        long tiempos;
        int iteraciones;

        mejorCamino = new int[graph.numVerts + 1];
        tiempos = 0;
        iteraciones = 100;

        // Este bucle repetiría el proceso X numero de veces (iteraciones), y luego calcularía 
        // la media de estos tiempo para así obtener nuestro tiempo de ejecución
        for (int i = 0; i < iteraciones; i++) {
            //Inicializamos la variable start con el instante inicial
            start = System.nanoTime();
            //LLamamos a nuestro método de backtracking para encontrar el camino más óptimo
            backtracking(graph.getVertice(ciudad - 1), graph, mejorCamino, mDistancias);
            //Capturamos el intante final
            stop = System.nanoTime();
            //Vamos guardando los periodos de tiempo que se han empleado en calcular el camino X número de veces
            tiempos += stop - start;
        }

        //Por último, mostramos por pantalla nuestro tiempo de ejecución
        long media = tiempos / iteraciones;
        System.out.println("\n");
        System.out.println("- Tiempo de ejecución del método Backtracking: " + media + " ns\n");
        System.out.println("Fin problema\n");
    }

    public static double[][] Ficheros(String fichero) {
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        double[][] mDistacias = null;

        try {
            // Apertura del f y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            file = new File(fichero);
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            // Lectura del f
            String linea;
            int contador = 0;
            boolean leerCordenadas = false;
            while ((linea = br.readLine()) != null) {// recorremos el f buscando solamente las ciudades
                if ("\n".equals(linea) || "EOF".equals(linea)) {
                    leerCordenadas = false;
                }

                if (leerCordenadas) {
                    contador++;// aumentamos 1 cada vez que encontramos una ciudad
                }
                if ("NODE_COORD_SECTION".equals(linea)) {
                    leerCordenadas = true;
                }
            }
            mDistacias = new double[contador][3];// creamos una matriz con todos las dimensiones de las ciudades encontrados en el f
            boolean fin = false;
            while (fin == false) {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                int i = 0;
                while ((linea = br.readLine()) != null) {// recorremos el f rellenando la matriz con todos los datos de cada una de las ciudades
                    if ("\n".equals(linea) || "EOF".equals(linea)) {
                        leerCordenadas = false;
                        if ("EOF".equals(linea)) {
                            fin = true;
                        }
                    }

                    if (leerCordenadas && i < contador) {

                        String linea2[] = linea.replace("   ", " ").replace("  ", " ").trim().split(" ");// eliminamos todos los espacios que no necesitamos del f y dibimos los datos de la ciudades por espacios intermedios
                        mDistacias[i][0] = Double.parseDouble(linea2[0]);// introducimos cada uno de los valores de las ciudades en el lugar correspondiente en la matriz mDistancias
                        mDistacias[i][1] = Double.parseDouble(linea2[1]);
                        mDistacias[i][2] = Double.parseDouble(linea2[2]);
                        i++;
                    }
                    if ("NODE_COORD_SECTION".equals(linea)) {
                        leerCordenadas = true;
                    }
                }
            }

            return mDistacias;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el f, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return mDistacias;
    }

    public static boolean seleccionado(int siguiente, int[] camino, int size) {// esta funcion comprueba si el elemento seleccionado en este momento se encuentra en el camino
        boolean ocupado = false;// booleano que muestra si ya esta en el camino
        for (int i = 0; i < size && ocupado == false; i++) {// recorremos las ciudades en el camino para ver si la ciudad introducida este en el camino
            if (camino[i] == siguiente) {
                ocupado = true;// si la ciudad esta ya en el camino ponemos el booleano a true
            }
        }
        return ocupado;//devuelve el booleano 
    }

    public static float getDistanciaTotal(double[][] distancia, int[] camino) {
        float distanciaTotal = 0;// inicializamos la distancia total
        for (int i = 0; i < camino.length - 1; i++) {// recorre el camino 
            distanciaTotal += getDistancia(distancia, camino[i], camino[i + 1]);//sumo la distancia de ciudad en ciudad obteniendo la distancia total del camino

        }
        return distanciaTotal;// devuelve la distancia total

    }

    public static double getDistancia(double[][] distancias, int inicio, int siguiente) {
        double primero = distancias[inicio][1] - distancias[siguiente][1];// obtiene el primer double de la formula
        double segundo = distancias[inicio][2] - distancias[siguiente][2];// obtiene el segundo double de la formula
        return Math.sqrt(Math.pow(primero, 2) + Math.pow(segundo, 2));// realiza la formula obteniendo la distancia entre las dos ciudad
    }

    public static String ImprimirCamino(int[] camino, double[][] distancias) {// imprime el camino 
        String s = "";
        for (int i = 0; i < camino.length - 1; i++) {// recorre la matriz distancia con el camino creado imprimiendo la ciudad seleccionada en el camino de la matriz distancia
            s += (int) distancias[camino[i]][0] + "-> ";// convierte en int el numero de la ciudad para que no salga como double quitando el 1.00,2.00,etx
        }
        s += (int) distancias[camino[camino.length - 1]][0] + "";
        return s;
    }

    public static int[] backtracking(Vertice inicio, Grafo graph, int[] caminoOptimo, double[][] distancia) {
        dfs(graph, inicio, calls, caminoOptimo, distancia);
        return caminoOptimo;
    }

    public static void dfs(Grafo graf, Vertice inicio, LinkedStack llamadas, int[] caminoOptimo, double[][] distancia) {//vuelta atras, recorre todos los caminos

        if (end == false) {//si end == false
            llamadas.push(inicio);// la pila introduce el vertice start
            inicio.setVisitado(true);// start pone el booleano visitado a false
        }
        int[] camino = pasoPilaArray(llamadas);//Obtenemos un camino candidato a ser el camino óptimo a partir de la pila.
        Vertice vert = (Vertice) llamadas.last();//Guardamos la ciudad inicial la cual empezamos el camino en una variable tipo vertice.
        camino[0] = vert.getNumVertice();//Guardamos la ciudad inicial en la ultima posicion del camino, indicando la vuelta a la ciudad inicial. 
        double dcamino = getDistanciaTotal(distancia, camino);//Obtenemos la distancia de el camino candidato.
        double dcaAc = getDistanciaTotal(distancia, caminoOptimo);//Obtenemos la distancia de el camino óptimo.
        if (dcamino > dcaAc && dcaAc != 0) {//comprobamos si nos sirve el elemento que acabamos de introducir
            llamadas.pop();// se saca el vertice de la pila
            inicio.setVisitado(false);// y se pone visitado a false
            inicio = (Vertice) llamadas.top();
        } else {
            Node vertice = inicio.lad.getFirstNode();//se captura el primera arista de la lista 
            while (end == false && vertice != null) {// mientras end sea false y vertice distinto de null
                Arista AristaActual = (Arista) vertice.getElement();// cogemos la arista de la lista
                if (AristaActual.destino.getVisitado() != true) {// si no ha sido visitado el vertice adyacente 
                    dfs(graf, AristaActual.destino, llamadas, caminoOptimo, distancia);// recursividad con el siguiente vertice adyacente
                }
                vertice = vertice.getNext();// si ha sido visitado miramos el siguiente vertice de la lista

            }
            if (vertice == null && end == false) {//si vertice es null y no se ha end
                if (llamadas.size() + 1 == caminoOptimo.length) {//Si el tamaño de la pila es igual al tamaño del camino óptimo.
                    camino = pasoPilaArray(llamadas);//Obtenemos un camino candidato a ser el camino óptimo a partir de la pila.
                    vert = (Vertice) llamadas.last();//Guardamos la ciudad inicial la cual empezamos el camino en una variable tipo vertice.
                    camino[0] = vert.getNumVertice();//Guardamos la ciudad inicial en la ultima posicion del camino, indicando la vuelta a la ciudad inicial. 
                    dcamino = getDistanciaTotal(distancia, camino);//Obtenemos la distancia de el camino candidato.
                    if (dcamino < dcaAc || getDistanciaTotal(distancia, caminoOptimo) == 0) {//Comparamos si la distancia del camino candidato es menor que la distancia del camino óptimo.
                        for (int i = 0; i < camino.length; i++) {//En el caso de que sea menor, se copiarán las ciudades del camino candidato en el camino óptimo.
                            caminoOptimo[i] = camino[i];
                        }
                    }
                }
                if (llamadas.isEmpty()) {//compara el start vertice inicial con el stop que queremos encontrar
                    end = true;// si es igual true
                }

                if (!end) {
                    llamadas.pop();// se saca el vertice de la pila
                    inicio.setVisitado(false);// y se pone visitado a false
                }

            }
        }
    }

    public static int[] pasoPilaArray(LinkedStack llamadas) {
        int[] camino = new int[llamadas.size() + 1];// se crea un array de vertices del tamaño de la pila
        int i = 0;
        while (i < camino.length - 1) {// se va sacando elementos de la pila e introduciendolos en el array
            Vertice vert = (Vertice) llamadas.topIndex(i);
            camino[i + 1] = vert.getNumVertice();
            i++;
        }
        return camino;
    }

}
