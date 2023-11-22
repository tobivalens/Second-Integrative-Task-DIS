package com.example.pipemaniagame.control;

import com.example.pipemaniagame.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class MatrixGameController {
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private MatrixGraph<Box> matrixGraph;

    private int pipesUsed;
    private long score;

    private Random random;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

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

    private int watersourceIndex, drainIndex;

    public MatrixGameController(Canvas ca){
        canvas=ca;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.startTime = LocalDateTime.now();
        this.endTime = null;
        matrixGraph= new MatrixGraph<>(64);
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

    private void drawBoard() {

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int cellSize = 64;
        int numRows = 8;
        int numCols = 8;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                Vertex vertex = matrixGraph.getVertexList().get(row * numCols + col);
                Box box = (Box)vertex.getContent();
                if (box != null) {
                    box.paint(x, y, cellSize, box);
                }
            }
        }
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
    public int coordinadeToIdex(int x, int y){
        return x*8+y;
    }
    public void addFullBoxes() {

        try{
            int counter = 0;

            int sourceRow, sourceCol, drainRow, drainCol;

            do {
                int source = setRandomNodeSource();
                sourceRow = source / 8;
                sourceCol = source % 8;

                int drain = setRandomNodeDrain();
                drainRow = drain / 8;
                drainCol = drain % 8;
            } while (Math.abs(sourceRow - drainRow) < 2|| Math.abs(sourceCol - drainCol) < 2);
            int source= coordinadeToIdex(sourceRow,sourceCol);
            int drain= coordinadeToIdex(drainRow,drainCol);

            for (int i = 0; i < 64; i++) {
                if (i == source) {
                    matrixGraph.addVertex((new Box(new Pipe(PipeType.WATERSOURCE), canvas)));
                    setWatersourceIndex(i);
                } else if (i == drain) {
                    matrixGraph.addVertex((new Box(new Pipe(PipeType.DRAIN), canvas)));
                    setDrainIndex(i);
                } else {
                    matrixGraph.addVertex((new Box(null, canvas)));
                }
            }

            for (int j = 0; j < 64; j++) {

                int row = j / 8;
                int col = j % 8;

                if (col < 7) {
                    matrixGraph.addEdge(j,j+1);
                    counter++;
                }
                if (col > 0) {
                    matrixGraph.addEdge(j, j - 1);
                    counter++;
                }
                if (row < 7) {
                    matrixGraph.addEdge(j, j + 8);
                    counter++;
                }
                if (row > 0) {
                    matrixGraph.addEdge(j, j - 8);
                    counter++;
                }
            }
            System.out.println(counter);

        }catch (Exception e){
            e.printStackTrace();
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

        for (int i = 0; i < matrixGraph.getVertexList().size(); i++) {
            if (i == coordinate) {
                Box b = (Box) matrixGraph.getVertexList().get(i).getContent();
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


    public boolean simulate() {
        Vertex<Box> waterSource = matrixGraph.indexVertex(getWatersourceIndex());
        Vertex<Box> drain = matrixGraph.indexVertex(getDrainIndex());

        for (int i = 0; i < matrixGraph.getVertexList().size(); i++) {
            Vertex<Box> c = matrixGraph.indexVertex(i);
            if (c.getContent().getContent() instanceof Pipe) {
                PipeType p = c.getContent().getContent().getPipeType();
                if (p == PipeType.CIRCULAR) {
                    matrixGraph.getMatrixAdj()[getWatersourceIndex()][i] = 0;
                }
            }
        }
        if (simulateBFS(waterSource, drain)) {
            System.out.println("entro al bfs");
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

            if (currentBox != null && currentBox.getContent() instanceof Pipe) {
                PipeType currentPipeType = currentBox.getContent().getPipeType();

                if (validateDrain() && currentPipeType == PipeType.DRAIN) {
                    return true;
                }

                int currentVertexIndex = matrixGraph.returnIndex(currentVertex);
                for (int i = 0; i < matrixGraph.getVertexList().size(); i++) {
                    if (matrixGraph.getMatrixAdj()[currentVertexIndex][i] == 1) {
                        Vertex<Box> nextTo = matrixGraph.indexVertex(i);
                        if (nextTo != null && nextTo.getContent() != null && nextTo.getContent().getContent() != null) {
                            PipeType pipeNextTo = nextTo.getContent().getContent().getPipeType();

                            if (!visitedVertex.contains(nextTo)) {
                                if (pipeNextTo == PipeType.VERTICAL && validateVertical(nextTo)) {
                                    queue.add(nextTo);
                                    visitedVertex.add(nextTo);
                                } else if (pipeNextTo == PipeType.HORIZONTAL && validateHorizontal(nextTo)) {
                                    queue.add(nextTo);
                                    visitedVertex.add(nextTo);
                                } else if (pipeNextTo != PipeType.HORIZONTAL && pipeNextTo != PipeType.VERTICAL) {
                                    queue.add(nextTo);
                                    visitedVertex.add(nextTo);
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean validateDrain() {
        int drainIndex = getDrainIndex();
        for (int i = 0; i < matrixGraph.getVertexList().size(); i++) {
            if (matrixGraph.getMatrixAdj()[drainIndex][i] == 1) {
                Vertex<Box> c = matrixGraph.indexVertex(i);
                if (c.getContent().getContent() instanceof Pipe) {
                    PipeType p = c.getContent().getContent().getPipeType();
                    if (p == PipeType.CIRCULAR) {
                        return false;
                    }
                }
            }
        }
        if(drainIndex>0){
            if (matrixGraph.getMatrixAdj()[drainIndex][drainIndex-1] == 1) {
                Vertex<Box> c = matrixGraph.indexVertex(drainIndex-1);
                if (c.getContent().getContent() instanceof Pipe) {
                    PipeType p = c.getContent().getContent().getPipeType();
                    if (p == PipeType.VERTICAL) {
                        return false;
                    }
                }
            }
        }

        if(drainIndex<7){
            if (matrixGraph.getMatrixAdj()[drainIndex][drainIndex+1] == 1) {
                Vertex<Box> c = matrixGraph.indexVertex(drainIndex+1);
                if (c.getContent().getContent() != null) {
                    PipeType p = c.getContent().getContent().getPipeType();
                    if (p == PipeType.VERTICAL) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean validateVertical(Vertex<Box> value) {
        int valueIndex = matrixGraph.returnIndex(value);//FIXME
        for (int i = 0; i < matrixGraph.getVertexList().size(); i++) {
            if (matrixGraph.getMatrixAdj()[valueIndex][i] == 1) {
                Vertex<Box> c = matrixGraph.indexVertex(i);
                if (c.getContent().getContent() != null) {
                    PipeType p = c.getContent().getContent().getPipeType();
                    if (p == PipeType.HORIZONTAL) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean validateHorizontal(Vertex value) {
        int valueIndex = matrixGraph.returnIndex(value);
        for (int i = 0; i < matrixGraph.getVertexList().size(); i++) {
            if (matrixGraph.getMatrixAdj()[valueIndex][i] == 1) {
                Vertex<Box> c = matrixGraph.indexVertex(i);
                if (c.getContent().getContent() != null) {
                    PipeType p = c.getContent().getContent().getPipeType();
                    if (p == PipeType.VERTICAL ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }





}
