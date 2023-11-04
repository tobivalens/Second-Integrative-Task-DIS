package ui;

import util.Graph;
import util.Vertex;

public class MainAdjList {
    public static void main(String[] args) {
        System.out.println("LISTA DE ADYACENCIA UNICAMENTE");
        Graph<String> graph = new Graph<>();

        //ESTE MAIN ES UNICAMENTE PARAPROBAR LAS FUNCIONALIDADES DE LA LISTA DE ADYACENCIA
        //NO ES OFICIAL

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        Vertex<String> vertexA = graph.searchVertex("A");
        Vertex<String> vertexB = graph.searchVertex("B");
        Vertex<String> vertexC = graph.searchVertex("C");
        Vertex<String> vertexD = graph.searchVertex("D");

        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexA, vertexC);
        graph.addEdge(vertexB, vertexD);
        graph.addEdge(vertexC, vertexD);

        System.out.println("Información del grafo:");
        System.out.println(graph);

        graph.deleteVertex("B");

        System.out.println("Información del grafo después de eliminar el vértice B:");
        System.out.println(graph);
    }
}