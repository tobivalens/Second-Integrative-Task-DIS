package util;

import java.util.ArrayList;

public class Graph<T>{


    ArrayList<Vertex> adjacencyList;

    public  Graph(){
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
}
