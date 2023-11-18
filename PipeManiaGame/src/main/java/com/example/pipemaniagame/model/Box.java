package com.example.pipemaniagame.model;

public class Box {
    Pipe content;

    public Box(Pipe content){
        this.content= content;
    }
    public Pipe getContent() {
        return content;
    }
    public void setContent(Pipe content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content.toString() ;
    }
}
