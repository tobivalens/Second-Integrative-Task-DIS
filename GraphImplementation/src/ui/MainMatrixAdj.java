package ui;

import util.MatrixGraph;
import util.Vertex;

public class MainMatrixAdj {

    public static void main(String[] args) {
        MatrixGraph<String> graph = new MatrixGraph<>(5);


        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");


        Vertex<String> vertexA = graph.serchVertex("A");
        Vertex<String> vertexB = graph.serchVertex("B");
        Vertex<String> vertexC = graph.serchVertex("C");
        graph.addEdge(vertexA, vertexB);
        graph.addEdge(vertexA, vertexC);
        graph.addEdge(vertexB, vertexC);


        System.out.println("Matriz de Adyacencia después de agregar aristas:");
        System.out.println(graph.printMatrix());

        // Eliminar un vértice
        graph.deleteVertex("A");

        // Mostrar la matriz de adyacencia después de eliminar un vértice
        System.out.println("\nMatriz de Adyacencia después de eliminar un vértice:");
        System.out.println(graph.printMatrix());
    }
}
