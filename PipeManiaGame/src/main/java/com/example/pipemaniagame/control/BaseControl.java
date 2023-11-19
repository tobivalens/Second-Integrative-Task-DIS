package com.example.pipemaniagame.control;

import com.example.pipemaniagame.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

public class BaseControl {
    private Canvas canvas;
    private GraphicsContext graphicsContext;


    private AdjacencyListGraph<Vertex> adjacencyGraph;

    private int pipesUsed;
    private long score;

    private Random random;



    private int watersourceIndex, drainIndex;

    public BaseControl(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();

        adjacencyGraph = new AdjacencyListGraph<>();
        pipesUsed = 0;
        score = 0;
        random = new Random();
        addFullBoxes();

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
            int source = setRandomNodeSource();
            int drain = setRandomNodeDrain();

            for (int i = 0; i < 64; i++) {
                if (i == source) {
                    adjacencyGraph.addVertex(new Vertex<>(new Box(new Pipe(PipeType.WATERSOURCE), canvas),new ArrayList<>()));
                    setWatersourceIndex(i);
                } else if (i == drain) {
                    adjacencyGraph.addVertex(new Vertex<>(new Box(new Pipe(PipeType.DRAIN), canvas),new ArrayList<>()));
                    setDrainIndex(i);
                } else {
                    adjacencyGraph.addVertex(new Vertex<>(new Box(null, canvas), new ArrayList<>()));
                }
            }

            for (int j = 0; j < adjacencyGraph.getAdjacencyList().size(); j++) {
                Vertex<Box> vertex1 = adjacencyGraph.getAdjacencyList().get(j).getContent();
                int row = j / 8;
                int col = j % 8;

                if (col < 7) {
                    Vertex<Box> vertexDer = adjacencyGraph.getAdjacencyList().get(j + 1).getContent();
                    vertex1.getAdjacencyListVertex().add(vertexDer);
                    counter++;
                }
                if (col > 0) {
                    Vertex<Box> vertexIzq = adjacencyGraph.getAdjacencyList().get(j - 1).getContent();
                    vertex1.getAdjacencyListVertex().add(vertexIzq);
                    counter++;
                }
                if (row < 7) {
                    Vertex<Box> vertexDown = adjacencyGraph.getAdjacencyList().get(j + 8).getContent();
                    vertex1.getAdjacencyListVertex().add(vertexDown);
                    counter++;
                }
                if (row > 0) {
                    Vertex<Box> vertexUp = adjacencyGraph.getAdjacencyList().get(j - 8).getContent();
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

                Vertex vertex = adjacencyGraph.getAdjacencyList().get(row * numCols + col).getContent();
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
                Box b = (Box) adjacencyGraph.getAdjacencyList().get(i).getContent().getContent();
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
        Vertex waterSource = adjacencyGraph.getAdjacencyList().get(getWatersourceIndex()).getContent();
        Vertex drain = adjacencyGraph.getAdjacencyList().get(getDrainIndex()).getContent();
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
                if (currentPipeType == PipeType.DRAIN) {

                    return true;
                }

                for (Vertex<Box> nextTo : currentVertex.getAdjacencyListVertex()) {

                    if (!visitedVertex.contains(nextTo)) {
                        queue.add(nextTo);
                        visitedVertex.add(nextTo);
                    }
                }
            }
        }

        return false;
    }

    public double calculateScore(){
        return 1.4;
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