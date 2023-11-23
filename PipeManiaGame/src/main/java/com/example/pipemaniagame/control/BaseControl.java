package com.example.pipemaniagame.control;

import com.example.pipemaniagame.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class BaseControl {
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private AdjacencyListGraph<Box> adjacencyGraph;

    private int pipesUsed;
    private long score;

    private Random random;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int watersourceIndex, drainIndex;

    public BaseControl(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.startTime = LocalDateTime.now();
        this.endTime = null;

        adjacencyGraph = new AdjacencyListGraph<>();
        pipesUsed = 0;
        score = 0;
        random = new Random();
        addFullBoxes();

    }

    public void finalizarPartida() {
        endTime = LocalDateTime.now();
    }

    /**
     * Description: This method calculates the lenght in seconds of the game according to it's start and end time
     * @return long with the duration of the game in seconds
     */

    public long getGameLenghtInSeconds(){
        long ret=0;
        if (startTime != null && endTime != null) {
            Duration duracion = Duration.between(startTime, endTime);
            ret= duracion.getSeconds();
        }
        else {
            ret= -1;
        }
        return ret;
    }

    public double calculateScore() {
        this.score = (100-pipesUsed)*10-getGameLenghtInSeconds();
        return this.score;
    }



    public void paint() {

        drawBoard();
    }

    public int setRandomNodeDrain() {
        int node = random.nextInt(64);
        return node;
    }

    /**
     * Description: This method creates a random coordinate to place the water source on the board and transforms it to an index
     *
     * @return the index where the water source must be placed
     */

    public int setRandomNodeSource() {
        int node = random.nextInt(64);
        return node;
    }

    public void addFullBoxes() {
        int counter=0;
        try {
            int sourceRow, sourceCol, drainRow, drainCol;

            do {
                int source = setRandomNodeSource();
                sourceRow = source / 8;
                sourceCol = source % 8;

                int drain = setRandomNodeDrain();
                drainRow = drain / 8;
                drainCol = drain % 8;
            } while (Math.abs(sourceRow - drainRow) < 3 || Math.abs(sourceCol - drainCol) < 3);
            int source= coordinadeToIdex(sourceRow,sourceCol);
            int drain= coordinadeToIdex(drainRow,drainCol);

            for (int i = 0; i < 64; i++) {
                if (i == source) {
                    adjacencyGraph.addVertex(new Box(new Pipe(PipeType.WATERSOURCE),canvas));
                    setWatersourceIndex(i);
                } else if (i == drain) {
                    adjacencyGraph.addVertex((new Box(new Pipe(PipeType.DRAIN), canvas)));
                    setDrainIndex(i);
                } else {
                    adjacencyGraph.addVertex((new Box(null, canvas)));
                }
            }

            for (int j = 0; j < adjacencyGraph.getAdjacencyList().size(); j++) {
                Vertex<Box> vertex1 = adjacencyGraph.getAdjacencyList().get(j);
                int row = j / 8;
                int col = j % 8;

                if (col < 7) {
                    Vertex<Box> vertexDer = adjacencyGraph.getAdjacencyList().get(j + 1);
                    vertex1.getAdjacencyListVertex().add(vertexDer);
                    counter++;
                }
                if (col > 0) {
                    Vertex<Box> vertexIzq = adjacencyGraph.getAdjacencyList().get(j - 1);
                    vertex1.getAdjacencyListVertex().add(vertexIzq);
                    counter++;
                }
                if (row < 7) {
                    Vertex<Box> vertexDown = adjacencyGraph.getAdjacencyList().get(j + 8);
                    vertex1.getAdjacencyListVertex().add(vertexDown);
                    counter++;
                }
                if (row > 0) {
                    Vertex<Box> vertexUp = adjacencyGraph.getAdjacencyList().get(j - 8);
                    vertex1.getAdjacencyListVertex().add(vertexUp);
                    counter++;
                }
            }
            System.out.printf(counter+"");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void drawBoard() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int cellSize = 64;
        int numRows = 8;
        int numCols = 8;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                Vertex vertex = adjacencyGraph.getAdjacencyList().get(row * numCols + col);
                Box box = (Box)vertex.getContent();
                if (box != null) {
                    box.paint(x, y, cellSize, box);
                }
            }
        }
    }

    public void insertPipe(int x, int y , int pipe){

        PipeType pipeType= PipeType.EMPTY;
        switch(pipe){
            case 1:
                pipeType=PipeType.VERTICAL;
                break;
            case 2:
                pipeType=PipeType.HORIZONTAL;
                break;
            case 3:
                pipeType=PipeType.CIRCULAR;
                break;
        }
        int coordinate= coordinadeToIdex(x,y);

        for (int i = 0; i < adjacencyGraph.getAdjacencyList().size(); i++) {
            if (i == coordinate) {
                Box b = (Box) adjacencyGraph.getAdjacencyList().get(i).getContent();
                if (b.getContent() != null && b.getContent().getPipeType().equals(PipeType.WATERSOURCE)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setContentText("You can not modify the water source");
                    alert.showAndWait();
                   break;
                } else if (b.getContent() != null && b.getContent().getPipeType().equals(PipeType.DRAIN)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setContentText("You can not modify the drain");
                    alert.showAndWait();
                    break;
                } else {
                    b.setContent(new Pipe(pipeType));
                    pipesUsed++;
                }
            }
        }
    }

    public boolean simulate(){
        Vertex waterSource = adjacencyGraph.getAdjacencyList().get(getWatersourceIndex());
        Vertex drain = adjacencyGraph.getAdjacencyList().get(getDrainIndex());

        for(int i=0; i< waterSource.getAdjacencyListVertex().size();i++){
            Vertex<Box> c= (Vertex<Box>) waterSource.getAdjacencyListVertex().get(i);
            if(c.getContent().getContent() instanceof  Pipe){
                PipeType p=c.getContent().getContent().getPipeType();
                if(p==PipeType.CIRCULAR){
                    waterSource.getAdjacencyListVertex().remove(i);
                }
            }
        }

        if( simulateBFS(waterSource,drain)){
            return true;
        }
        return false;
    }

    public boolean simulateBFS(Vertex<Box> fountVertex, Vertex<Box> drainVertex) {
        Queue<Vertex<Box>> queue = new LinkedList<>();
        Set<Vertex<Box>> visitedVertex = new HashSet<>();

        queue.add(fountVertex);
        visitedVertex.add(fountVertex);

        while (!queue.isEmpty()) {
            Vertex<Box> currentVertex = queue.poll();
            Box currentBox = currentVertex.getContent();

            if (currentBox.getContent() instanceof Pipe) {
                PipeType currentPipeType = ((Pipe) currentBox.getContent()).getPipeType();

                if (validateDrain()&& currentPipeType == PipeType.DRAIN) {
                    return true;
                }

                for (Vertex<Box> nextTo : currentVertex.getAdjacencyListVertex()) {
                    if(nextTo.getContent().getContent()!=null){
                        PipeType pipeNextto= nextTo.getContent().getContent().getPipeType();
                        if(pipeNextto==PipeType.VERTICAL){
                            if (validateVertical(nextTo)&&!visitedVertex.contains(nextTo)) {
                                queue.add(nextTo);
                                visitedVertex.add(nextTo);
                            }
                        }
                        else if(pipeNextto==PipeType.HORIZONTAL){
                            if (validateHorizontal(nextTo)&&!visitedVertex.contains(nextTo)) {
                                queue.add(nextTo);
                                visitedVertex.add(nextTo);
                            }
                        }
                        else{
                            if (!visitedVertex.contains(nextTo)) {
                                queue.add(nextTo);
                                visitedVertex.add(nextTo);
                            }

                        }

                    }

                }
            }
        }
        return false;
    }

    public boolean validateDrain(){
        Vertex drain = adjacencyGraph.getAdjacencyList().get(getDrainIndex());
        for(int i=0; i< drain.getAdjacencyListVertex().size();i++){
            Vertex<Box> c= (Vertex<Box>) drain.getAdjacencyListVertex().get(i);
            if(c.getContent().getContent() instanceof  Pipe){
                PipeType p=c.getContent().getContent().getPipeType();
                if(p==PipeType.CIRCULAR){
                    return false;
                }
            }
        }
        return true;
    }

   public boolean validateVertical(Vertex<Box> value){
       for(int i=0; i< value.getAdjacencyListVertex().size();i++){
           Vertex<Box> c= (Vertex<Box>) value.getAdjacencyListVertex().get(i);
           if(c.getContent().getContent() != null){
               PipeType p=c.getContent().getContent().getPipeType();
               if(p==PipeType.HORIZONTAL){
                   return false;
               }
           }
       }
       return true;
   }

   public boolean validateHorizontal(Vertex<Box> value){
       for(int i=0; i< value.getAdjacencyListVertex().size();i++){
           Vertex<Box> c= (Vertex<Box>) value.getAdjacencyListVertex().get(i);
           if(c.getContent().getContent() != null){
               PipeType p=c.getContent().getContent().getPipeType();
               if(p==PipeType.VERTICAL){
                   return false;
               }
           }
       }
       return true;
   }

   //Falta implementar Dijsktra

    public boolean earierSolutionActiveButton(){
        Vertex waterSource = adjacencyGraph.getAdjacencyList().get(getWatersourceIndex());
        Vertex drain = adjacencyGraph.getAdjacencyList().get(getDrainIndex());
        showShorterSolution(waterSource,drain);
        return false;
    }

    public void showShorterSolution(Vertex<Box> source, Vertex<Box> drain) {
        List<Vertex<Box>> shortestPath = easierSolutionDijkstra(source, drain);

        for (Vertex<Box> pathVertex : shortestPath) {
            for (Vertex<Box> graphVertex : adjacencyGraph.getAdjacencyList()) {
                if (pathVertex == graphVertex) {
                    Box box = (Box) graphVertex.getContent();
                    box.setShortActivate(true);
                    System.out.println("si la di ois");
                }
            }
        }
    }
    public List<Vertex<Box>> easierSolutionDijkstra(Vertex<Box> source, Vertex<Box> drain) {
        // Inicialización de las estructuras de datos necesarias
        Map<Vertex<Box>, Integer> distance = new HashMap<>();  // Almacena las distancias desde la fuente hasta cada nodo
        Map<Vertex<Box>, Vertex<Box>> predecessor = new HashMap<>();  // Almacena los predecesores de cada nodo en el camino más corto
        PriorityQueue<Vertex<Box>> minHeap = new PriorityQueue<>(Comparator.comparingInt(distance::get));  // Cola de prioridad basada en las distancias

        // Inicialización de la distancia del nodo fuente como 0
        distance.put(source, 0);

        // Añade el nodo fuente a la cola de prioridad
        minHeap.add(source);

        // Bucle principal de Dijkstra
        while (!minHeap.isEmpty()) {
            // Obtiene el nodo con la distancia mínima desde la fuente
            Vertex<Box> currentVertex = minHeap.poll();

            // Explora los vecinos del nodo actual
            for (Vertex<Box> neighbor : currentVertex.getAdjacencyListVertex()) {
                // Calcula la nueva distancia desde la fuente hasta el vecino
                int newDistance = distance.get(currentVertex) + 1;

                // Actualiza la distancia y el predecesor si se encuentra un camino más corto
                if (!distance.containsKey(neighbor) || newDistance < distance.get(neighbor)) {
                    if(restriction(currentVertex,neighbor,minHeap)){
                        distance.put(neighbor, newDistance);
                        predecessor.put(neighbor, currentVertex);
                        minHeap.add(neighbor);  // Añade el vecino a la cola de prioridad para explorar sus vecinos
                    }
                }
            }
        }


        return reconstructPath(source, drain, predecessor);
    }


    private List<Vertex<Box>> reconstructPath(Vertex<Box> source, Vertex<Box> drain, Map<Vertex<Box>, Vertex<Box>> predecessor) {
        List<Vertex<Box>> path = new ArrayList<>();
        Vertex<Box> current = drain;
        //revierte el camino
        while (current != null && current != source) {
            path.add(current);
            current = predecessor.get(current);
        }

        // Si la ruta del drenaje es valida, invierte el camino para que vaya de la fuente al drenaje
        if (current == source) {
            path.add(source);
            Collections.reverse(path);
        } else {
            path.clear();  // si no ps queda vacio
        }

        return path;
    }


    public boolean restriction(Vertex<Box>actual, Vertex<Box>neighbor, PriorityQueue<Vertex<Box>> minHeap){
        int index= adjacencyGraph.getAdjacencyList().indexOf(actual);
        int neighIndex= adjacencyGraph.getAdjacencyList().indexOf(neighbor);


        if(neighbor.getContent().getContent()!=null &&neighbor.getContent().getContent().getPipeType()==PipeType.WATERSOURCE){
            return true;
        }
        //neighbr seria el pre, el pre pre seria pre neighbor

        //si esta abajo
        if(neighIndex==index+8){
            Vertex<Box>preNeighbor;

            // caso uno pre pre a la izquierda
            preNeighbor= adjacencyGraph.getAdjacencyList().get(neighIndex-1) ;
            int preNeighIndex= adjacencyGraph.getAdjacencyList().indexOf(preNeighbor);
            if(minHeap.contains(preNeighbor)){
                Vertex<Box> upPreNeighbor= adjacencyGraph.getAdjacencyList().get(preNeighIndex-8);
                Vertex<Box> downPreNeighbor= adjacencyGraph.getAdjacencyList().get(preNeighIndex+8);
                if(minHeap.contains(upPreNeighbor)|| minHeap.contains(downPreNeighbor)){
                    return false;
                }
            }

            Vertex<Box>preNeighborDer=adjacencyGraph.getAdjacencyList().get(neighIndex+1);
            int preNeighIndexDer= adjacencyGraph.getAdjacencyList().indexOf(preNeighborDer);

            // caso dos pre pre a la derecha
             preNeighbor= adjacencyGraph.getAdjacencyList().get(neighIndex+1) ;
              if(minHeap.contains(preNeighbor)){
                Vertex<Box> upPreNeighbor= adjacencyGraph.getAdjacencyList().get(preNeighIndexDer-8);
                Vertex<Box> downPreNeighbor= adjacencyGraph.getAdjacencyList().get(preNeighIndexDer+8);
                if(minHeap.contains(upPreNeighbor)|| minHeap.contains(downPreNeighbor)){
                    return false;
                }
            }

            //creo que el de arriba no se puede dado que arriba esta el actual y el de abajo no importa pq es valido
        }else if(neighIndex==index-8)//si esta arriba
        {
            Vertex<Box>preNeighbor;
            //si esta a la izq del neighbor
            preNeighbor= adjacencyGraph.getAdjacencyList().get(neighIndex+1) ;

            Vertex<Box>preNeighborIzq=adjacencyGraph.getAdjacencyList().get(neighIndex-1);
            int preNeighIndex= adjacencyGraph.getAdjacencyList().indexOf(preNeighborIzq);

            if(minHeap.contains(preNeighbor)){
                Vertex<Box> upPreNeighbor= adjacencyGraph.getAdjacencyList().get(preNeighIndex-8);
                Vertex<Box> downPreNeighbor= adjacencyGraph.getAdjacencyList().get(preNeighIndex+8);
                if(minHeap.contains(upPreNeighbor)|| minHeap.contains(downPreNeighbor)){
                    return false;
                }
            }
            //si esta a la der del neighbor
            Vertex<Box>preNeighborDer=adjacencyGraph.getAdjacencyList().get(neighIndex+1);
            int preNeighIndexDer= adjacencyGraph.getAdjacencyList().indexOf(preNeighborDer);

            preNeighbor= adjacencyGraph.getAdjacencyList().get(neighIndex-1) ;
            if(minHeap.contains(preNeighbor)){
                Vertex<Box> upPreNeighbor= adjacencyGraph.getAdjacencyList().get(preNeighIndexDer-8);
                Vertex<Box> downPreNeighbor= adjacencyGraph.getAdjacencyList().get(preNeighIndexDer+8);
                if(minHeap.contains(upPreNeighbor)|| minHeap.contains(downPreNeighbor)){
                    return false;
                }
            }

        } else if (neighIndex==index+1) {//derecha
            Vertex<Box>preNeighbor;
            // caso uno pre pre a la arriba
            preNeighbor= adjacencyGraph.getAdjacencyList().get(neighIndex-8) ;
            int indexPreNeighbot= adjacencyGraph.getAdjacencyList().indexOf(preNeighbor);
            if(minHeap.contains(preNeighbor)){
                Vertex<Box> leftPreNeighbor= adjacencyGraph.getAdjacencyList().get(indexPreNeighbot-1);
                Vertex<Box> rightPreNeighbor= adjacencyGraph.getAdjacencyList().get(indexPreNeighbot+1);
                if(minHeap.contains(leftPreNeighbor)|| minHeap.contains(rightPreNeighbor)){
                    return false;
                }
            }


            preNeighbor= adjacencyGraph.getAdjacencyList().get(neighIndex+8) ;
            indexPreNeighbot=adjacencyGraph.getAdjacencyList().indexOf(preNeighbor);
            if(minHeap.contains(preNeighbor)){
                Vertex<Box> leftPreNeighbor= adjacencyGraph.getAdjacencyList().get(indexPreNeighbot-1);
                Vertex<Box> rightPreNeighbor= adjacencyGraph.getAdjacencyList().get(indexPreNeighbot+1);
                if(minHeap.contains(leftPreNeighbor)|| minHeap.contains(rightPreNeighbor)){
                    return false;
                }
            }


        }else if (neighIndex==index-1){ //izquierda

            Vertex<Box>preNeighbor;
            // caso uno pre pre a la abajo
            preNeighbor= adjacencyGraph.getAdjacencyList().get(neighIndex+8);
            int indexPreNeighbot= adjacencyGraph.getAdjacencyList().indexOf(preNeighbor);

            if(minHeap.contains(preNeighbor)){
                Vertex<Box> leftPreNeighbor= adjacencyGraph.getAdjacencyList().get(indexPreNeighbot-1);
                Vertex<Box> rightPreNeighbor= adjacencyGraph.getAdjacencyList().get(indexPreNeighbot+1);
                if(minHeap.contains(leftPreNeighbor)|| minHeap.contains(rightPreNeighbor)){
                    return false;
                }
            }

            preNeighbor= adjacencyGraph.getAdjacencyList().get(neighIndex-8) ;
            indexPreNeighbot=adjacencyGraph.getAdjacencyList().indexOf(preNeighbor);
            if(minHeap.contains(preNeighbor)){
                Vertex<Box> leftPreNeighbor= adjacencyGraph.getAdjacencyList().get(indexPreNeighbot-1);
                Vertex<Box> rightPreNeighbor= adjacencyGraph.getAdjacencyList().get(indexPreNeighbot+1);
                if(minHeap.contains(leftPreNeighbor)|| minHeap.contains(rightPreNeighbor)){
                    return false;
                }
            }

        }

        return true;
    }

    public void deleteGraph(){// falta hacer lo de que en exit llame a este metodo

    }

    public int coordinadeToIdex(int x, int y){
        return x*8+y;
    }

    public int getWatersourceIndex() {
        return watersourceIndex;
    }
    public void setWatersourceIndex(int watersourceIndex) {
        this.watersourceIndex = watersourceIndex;
    }

    public int getDrainIndex() {
        return drainIndex;
    }

    public void setDrainIndex(int drainIndex) {
        this.drainIndex = drainIndex;
    }
}