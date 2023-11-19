package com.example.pipemaniagame.control;

import com.example.pipemaniagame.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private Canvas canva;
    private GraphicsContext graphicsContext;

    private AdjacencyListGraph<Vertex> adjacencyGraph;

    private int pipesUsed;
    private long score;

    private Random random;

    public GameController(Canvas canvas){
        this.canva=canvas;
        this.graphicsContext = this.canva.getGraphicsContext2D();
        adjacencyGraph=new AdjacencyListGraph<>();
        pipesUsed=0;
        score=0;
        addFullBoxes();
        generateBoard();
        random= new Random();
    }

    public void paint(){
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canva.getWidth(), canva.getHeight());
    }
    public int setRandomNodeDrain() {
        int node = random.nextInt(64);
        return node;
    }
    /**
     * Description: This method creates a random coordinate to place the water source on the board and transforms it to an index
     * @return the index where the water source must be placed
     */

    public int setRandomNodeSource() {
        int node = random.nextInt(64);
        return node;
    }

    public void addFullBoxes() {

    }

    public void generateBoard(){

    }
}
