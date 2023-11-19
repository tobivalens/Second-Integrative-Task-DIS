package com.example.pipemaniagame.control;

import com.example.pipemaniagame.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class BaseControl {
    private Canvas canvas;
    private GraphicsContext graphicsContext;


    private AdjacencyListGraph<Vertex> adjacencyGraph;

    private int pipesUsed;
    private long score;

    private Random random;

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
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
        try {
            int source = setRandomNodeSource();
            int drain = setRandomNodeDrain();

            for (int i = 0; i < 64; i++) {
                if (i == source) {
                    adjacencyGraph.addVertex(new Vertex<Box>(new Box(new Pipe(PipeType.WATERSOURCE), canvas)));
                } else if (i == drain) {
                    adjacencyGraph.addVertex(new Vertex<Box>(new Box(new Pipe(PipeType.DRAIN), canvas)));
                } else {
                    adjacencyGraph.addVertex(new Vertex<Box>(new Box(null, canvas), new ArrayList<Vertex<Box>>()));
                }
            }
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
}