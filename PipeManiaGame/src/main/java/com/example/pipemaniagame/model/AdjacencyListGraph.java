package com.example.pipemaniagame.model;

import java.util.*;

public class AdjacencyListGraph<T>{
    public ArrayList<Vertex<T>> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(ArrayList<Vertex<T>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    ArrayList<Vertex<T>> adjacencyList;

    public AdjacencyListGraph(){
        adjacencyList= new ArrayList<>();
    }

    public boolean addVertex(T contentN){
        if(contentN ==null){
            return false;
        }
        boolean result=false;
        Vertex<T> newVertex= new Vertex<>(contentN, new ArrayList<>());
        if (adjacencyList.add(newVertex) ){
            result = true;
            return  result;
        }
        return result;
    }

    public boolean addEdge(Vertex<T>v1, Vertex<T>v2){
        if(searchValue(v1)||searchValue(v2)){
            v1.adjacencyListVertex.add(v2);
            v2.adjacencyListVertex.add(v1);
            return true;
        }
        return false;
    }
    public boolean searchValue(Vertex<T> node){
        T cont= node.getContent();
        for(Vertex<T> ver: adjacencyList){
            if(ver.getContent().equals(cont)){
                return true;
            }
        }
        return false;
    }

    public Vertex<T> searchVertex(T content){
        Vertex<T> found= null;
        for (Vertex<T> aux: adjacencyList) {
            if(aux.getContent().equals(content)){
                found= aux;
                return found;
            }
        }
        return found;
    }

    public boolean deleteVertex(T contentN){
        Vertex<T> vertexSearching = searchVertex(contentN);
        boolean result = false;
        if (vertexSearching != null) {
            for (Vertex<T> vertex : adjacencyList) {
                if (vertex.adjacencyListVertex.contains(vertexSearching)) {
                    vertex.adjacencyListVertex.remove(vertexSearching);
                }
            }
            adjacencyList.remove(vertexSearching);
            result = true;
            return result;
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph Information: \n");
        sb.append("Vertices: \n");
        for (Vertex<T> vertex : adjacencyList) {
            sb.append(vertex.getContent()).append("\n");
        }
        sb.append("Edges: \n");
        for (Vertex<T> vertex : adjacencyList) {
            for (Vertex<T> adjacent : vertex.adjacencyListVertex) {
                sb.append(vertex.getContent()).append(" - ").append(adjacent.getContent()).append("\n");
            }
        }
        return sb.toString();
    }

    public List<Vertex<T>> easierSolutionDijkstra(Vertex<T> source, Vertex<T> drain) {
        Map<Vertex<T>, Integer> distance = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> predecessor = new HashMap<>();
        PriorityQueue<Vertex<T>> minHeap = new PriorityQueue<>(Comparator.comparingInt(distance::get));

        distance.put(source, 0);

        minHeap.add(source);

        while (!minHeap.isEmpty()) {
            Vertex<T> currentVertex = minHeap.poll();


            for (Vertex<T> neighbor : currentVertex.getAdjacencyListVertex()) {
                int newDistance = distance.get(currentVertex) + 1;

                if (!distance.containsKey(neighbor) || newDistance < distance.get(neighbor)) {

                    distance.put(neighbor, newDistance);
                    predecessor.put(neighbor, currentVertex);
                    minHeap.add(neighbor);

                }
            }
        }
        return reconstructPath(source, drain, predecessor);
    }


    private List<Vertex<T>> reconstructPath(Vertex<T> source, Vertex<T> drain, Map<Vertex<T>, Vertex<T>> predecessor) {
        List<Vertex<T>> path = new ArrayList<>();
        Vertex<T> current = drain;
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




    public Vertex<T> searchValueIndex(int index){
       return  adjacencyList.get(index);
    }
}
