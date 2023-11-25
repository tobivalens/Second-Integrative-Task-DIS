package com.example.pipemaniagame.model;

import java.util.*;

public class MatrixGraph<T> {

    private int maxVertex;
    private ArrayList<Vertex<T>> vertexList;
    private int[][] matrixAdj;

    public MatrixGraph(int maxVertex){
        this.maxVertex=maxVertex;
        vertexList= new ArrayList<>();
        matrixAdj= new int[maxVertex][maxVertex];
        initializeMatrix();
    }

    public void initializeMatrix(){
        for (int i = 0; i < maxVertex; i++) {
            for (int j = 0; j < maxVertex; j++) {
                matrixAdj[i][j] = 0;
            }
        }
    }

    public boolean addVertex(T content){// probada
        if (content==null) {
            return false;
        }
        Vertex<T> newVertex= new Vertex<>(content);
        if(vertexList.add(newVertex)){
            return true;
        }
        return false;
    }

    public boolean addEdge(int i, int j){

        if(!vertexList.isEmpty()&& i>=0 &&j>=0){
            matrixAdj[i][j]= 1;
            return true;
        }
      return false;
    }

    public int returnIndex(Vertex<T>v1){//probada
        for(int i=0; i<vertexList.size(); i++){
            if(vertexList.get(i).getContent().equals(v1.getContent())){
                return i;
            }
        }
        return -1;
    }

    public Vertex<T> indexVertex(int i){//probada
        if(!vertexList.isEmpty()&&vertexList.get(i)!=null){
            return vertexList.get(i);

        }
        return null;
    }

    public Vertex<T> serchVertex(T content){//probada
        Vertex<T> found= null;
        for (Vertex<T> aux: vertexList) {
            if(aux.getContent().equals(content)){
                found= aux;
                return found;
            }
        }
        return found;
    }

    public boolean deleteVertex(T content) {
        Vertex<T> vertex = serchVertex(content);
        if (vertex != null) {
            int indexToRemove = returnIndex(vertex);

            for (int i = 0; i < maxVertex; i++) {
                matrixAdj[indexToRemove][i] = 0;
            }

            for (int i = 0; i < maxVertex; i++) {
                matrixAdj[i][indexToRemove] = 0;
            }
            if(vertexList.remove(vertex)){
                return true;
            }
        }
        return false;
    }

    public int getMaxVertex() {
        return maxVertex;
    }

    public void setMaxVertex(int maxVertex) {
        this.maxVertex = maxVertex;
    }

    public ArrayList<Vertex<T>> getVertexList() {
        return vertexList;
    }

    public void setVertexList(ArrayList<Vertex<T>> vertexList) {
        this.vertexList = vertexList;
    }

    public int[][] getMatrixAdj() {
        return matrixAdj;
    }

    public void setMatrixAdj(int[][] matrixAdj) {
        this.matrixAdj = matrixAdj;
    }

    public String printMatrix() {
        String msg="";
        for (Vertex<T> vertex : vertexList) {
            msg+=(vertex.getContent() + " ");
        }
        msg+="\n";
        for (int i = 0; i < maxVertex-1; i++) {
            msg+=(vertexList.get(i).getContent() + " ");
            for (int j = 0; j < maxVertex; j++) {
                msg+=(matrixAdj[i][j] + " ");
            }
            msg+="\n";
        }
        return msg;
    }


    public List<Vertex<T>> dijkstra(Vertex<T> source, Vertex<T> drain) {
        Map<Vertex<T>, Integer> distance = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> predecessor = new HashMap<>();
        Set<Vertex<T>> settled = new HashSet<>();

        distance.put(source, 0);

        while (true) {
            Vertex<T> current = getClosestVertex(distance, settled);
            if (current == null) {
                break;
            }

            settled.add(current);

            for (Vertex<T> neighbor : getNeighbors(current)) {
                if (!settled.contains(neighbor)) {
                    int newDistance = distance.get(current) + 1;

                    if (!distance.containsKey(neighbor) || newDistance < distance.get(neighbor)) {
                        distance.put(neighbor, newDistance);
                        predecessor.put(neighbor, current);
                    }
                }
            }
        }

        return reconstructPath(source, drain, predecessor);
    }
    public Vertex<T> getClosestVertex(Map<Vertex<T>, Integer> distance, Set<Vertex<T>> settled) {
        Vertex<T> closestVertex = null;
        int minDistance = Integer.MAX_VALUE;

        for (Map.Entry<Vertex<T>, Integer> entry : distance.entrySet()) {
            Vertex<T> vertex = entry.getKey();
            int dist = entry.getValue();

            if (!settled.contains(vertex) && dist < minDistance) {
                minDistance = dist;
                closestVertex = vertex;
            }
        }

        return closestVertex;
    }

    public List<Vertex<T>> getNeighbors(Vertex<T> vertex) {
        List<Vertex<T>> neighbors = new ArrayList<>();
        int vertexIndex = getVertexList().indexOf(vertex);

        for (int i = 0; i < getMaxVertex(); i++) {
            if (getMatrixAdj()[vertexIndex][i] == 1) {
                neighbors.add(indexVertex(i));
            }
        }

        return neighbors;
    }


    public List<Vertex<T>> reconstructPath(Vertex<T> source, Vertex<T> drain, Map<Vertex<T>, Vertex<T>> predecessor) {
        List<Vertex<T>> path = new ArrayList<>();
        Vertex<T> current = drain;

        while (current != null && current != source) {
            path.add(current);
            current = predecessor.get(current);
        }

        if (current == source) {
            path.add(source);
            Collections.reverse(path);
        } else {
            path.clear();
        }

        return path;
    }
}

