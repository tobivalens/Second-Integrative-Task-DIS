package com.example.pipemaniagame.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class Box {
    Canvas canva;
    GraphicsContext graphicsContext;
    Pipe content;

    public boolean isShortActivate() {
        return shortActivate;
    }

    public void setShortActivate(boolean shortActivate) {
        this.shortActivate = shortActivate;
    }

    boolean shortActivate;


    public Box(Pipe content, Canvas canva){
        this.canva=canva;
        this.graphicsContext = this.canva.getGraphicsContext2D();
        this.content= content;
        this.shortActivate=false;
    }
    public Pipe getContent() {
        return content;
    }
    public void setContent(Pipe content) {
        this.content = content;
    }


    private static Image waterSourceImage = loadImage("/images/5.png");
    private static Image drainImage = loadImage("/images/6.png");
    private static Image horizontalImage = loadImage("/images/4.png");
    private static Image verticalImage = loadImage("/images/2.png");
    private static Image circularImage = loadImage("/images/3.png");
    private static Image defaultImage = loadImage("/images/1.png");
    private static Image shortActivateImage = loadImage("/images/pista.png");

    private static Image loadImage(String imagePath) {
        try (InputStream inputStream = Box.class.getResourceAsStream(imagePath)) {
            return new Image(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void paint(int x, int y, int size, Box box) {
        if (box != null) {
            PipeType pipeType = box.getContent() != null ? box.getContent().getPipeType() : null;

            if (pipeType == PipeType.WATERSOURCE) {
                drawImage(x, y, waterSourceImage);
            } else if (pipeType == PipeType.DRAIN) {
                drawImage(x, y, drainImage);
            } else if (pipeType == PipeType.HORIZONTAL) {
                drawImage(x, y, horizontalImage);
            } else if (pipeType == PipeType.VERTICAL) {
                drawImage(x, y, verticalImage);
            } else if (pipeType == PipeType.CIRCULAR) {
                drawImage(x, y, circularImage);
            } else if (shortActivate) {
                drawImage(x, y, shortActivateImage);
            } else {
                drawImage(x, y, defaultImage);
            }
        }
    }

    private void drawImage(int x, int y, Image image) {
        if (image != null) {
            graphicsContext.drawImage(image, x, y);
        }
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
