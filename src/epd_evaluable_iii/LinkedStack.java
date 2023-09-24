package epd_evaluable_iii;

public class LinkedStack<E> implements IStack<E> {

    private Node<E> firstNode;//primer nodo
    private Node<E> lastNode;//ultimo nodo
    private int size;//numero de nodos

    public LinkedStack() {
        firstNode = null;//inicializo el primer nodo
        lastNode = null;//inciializo el ultimo nodo
        size = 0;// cantidad de nodos en la  pila enlazada
    }

    @Override
    public int size() {
        return size;// devuelve el numero de nodos
    }

    @Override
    public boolean isEmpty() {
        return size==0;// si size es 0 esta vacio true
    }

    @Override
    public E top() throws IndexOutOfBoundsException {// solo muesta el ultimo que entro
        if (firstNode != null) {//si hay un primer elemento lo devuelve
            return firstNode.getElement();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    //Introducir elementos en la pila
    //Método estándar push, introducir elementos en la Linked Stack
    public void push(E element) throws IndexOutOfBoundsException {
        if (element != null) {
            //Si el element no es nulo creamos nuevo nodo auxiliar y lo guardamos en este
            Node<E> newNode = new Node<E>();
            newNode.setElement(element);
            //Comprobamos que la LinkedStack no está vacía
            if (lastNode != null && firstNode != null) {
                //El nuevo nodo pone como siguiente el que habia en primer lugar
                //Y así empezamos a recorrer la Pila
                newNode.setNext(firstNode);
                //El nuevo nodo se coloca primero
                firstNode = newNode;
            } else {
                //Si la pila está vacía
                newNode.setNext(null);
                //El ultimo y primer nodo son ahora el nodo nuevo
                lastNode = newNode;
                firstNode = newNode;
            }
            //Incrementamos a 1 el tamaño de la pila
            size++;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    //Sacar un elemento de la LinkedStack
    // Método estándar pop, sacar elementos de la Pila
    public E pop() throws IndexOutOfBoundsException {
        if (firstNode != null) {
            //Si la Pila no está vacía empezamos a recorrerla
            if (firstNode == lastNode) {                
                //Si la pila nada más tiene un solo elemento devolvemos el primer Nodo
                Node<E> nodeDevolver = new Node<E>();
                nodeDevolver = firstNode;
                //Lo ponemos a NULL y decrementamos 
                firstNode = null;
                size--;
                //Devolvemos el nodo donde hemos guardadp firstNode
                return nodeDevolver.getElement();
            } else {
                Node<E> nodeDevolver = new Node<E>();
                Node<E> nodeNuevo = new Node<E>();
                nodeDevolver = firstNode;//el primer nodo sera el que se debe devolver
                nodeNuevo = firstNode.getNext();// se le da valor al nodo nuevo
                firstNode.setNext(null);// // primer nodo pierde el elemento siguiente
                firstNode = nodeNuevo;// el primer nodo es el nuevo
                size--;// se resta un elemento 
                return nodeDevolver.getElement();// se devuelve el elemento que estaba en primer lugar

            }

        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {// to string de los nodos
        Node<E> newNode = new Node<E>();// se crea un nodo
        newNode = firstNode;//nodo nuevo primer nodo
        String s = newNode.toString() + " ";//se añade al string el tostring del nodo
        while (newNode.getNext() != null) {// si tiene siguiente
            s += newNode.getNext().toString() + " ";//se añade tostring al string
            newNode = newNode.getNext();// el nodo ahora es el siguiente
        }
        return s;// devuelve el string
    }
    public E topIndex(int index){//Mñetodo para obtener los elementos de la pila en función de un índice.
        Node<E> nodo = new Node<E>();// se crea un nodo
        nodo = firstNode;
        for(int i=0;i<=index;i++){//Realizamos un recorrido por la pila.
            if(index==i){//Si hemos encontrado ese nodo a través de la variable índice.
                return nodo.getElement();//Devuelve el elemento del nodo
            } else{//En caso contrario
                nodo= nodo.getNext();//Seguimos recorriendo.
            }
        }
        return null;//Si no lo encontramos, devuelve null.
    }
    public E last(){//Método para obtener el primer elemento insertado en la pila
         if (lastNode != null) {//si hay un primer elemento lo devuelve
            return lastNode.getElement();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

}
