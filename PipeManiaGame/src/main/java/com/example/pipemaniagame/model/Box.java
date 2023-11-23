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

    public void paint(int x, int y, int size, Box box) {
        if (box != null) {
            PipeType pipeType = box.getContent() != null ? box.getContent().getPipeType() : null;

            if (pipeType == PipeType.WATERSOURCE) {
                drawImage(x, y, "/images/5.png");
            } else if (pipeType == PipeType.DRAIN) {
                drawImage(x, y, "/images/6.png");
            } else if (pipeType == PipeType.HORIZONTAL) {
                drawImage(x, y, "/images/4.png");
            } else if (pipeType == PipeType.VERTICAL) {

                drawImage(x, y, "/images/2.png");
            } else if (pipeType == PipeType.CIRCULAR) {
                drawImage(x, y, "/images/3.png");
            } else if(shortActivate) {
               drawImage(x,y,"/images/pista.png");
            }
            else{
                drawImage(x, y, "/images/1.png");
            }
        }
    }

    private void drawImage(int x, int y, String imagePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            Image image = new Image(inputStream);
            graphicsContext.drawImage(image, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return content.toString();
    }
}
