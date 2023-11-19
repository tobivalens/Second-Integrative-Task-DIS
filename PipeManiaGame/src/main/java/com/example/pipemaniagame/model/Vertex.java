package com.example.pipemaniagame.model;

import java.util.ArrayList;

public class Vertex<T> {
    T content;

    public ArrayList<Vertex<T>> getAdjacencyListVertex() {
        return adjacencyListVertex;
    }

    public void setAdjacencyListVertex(ArrayList<Vertex<T>> adjacencyListVertex) {
        this.adjacencyListVertex = adjacencyListVertex;
    }

    ArrayList<Vertex<T>> adjacencyListVertex;

    public Vertex(T cont, ArrayList<Vertex<T>> adjVertex){
        this.content= cont;
        this.adjacencyListVertex= adjVertex;
    }

    public Vertex(T cont){
        this.content= cont;

    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }


}
