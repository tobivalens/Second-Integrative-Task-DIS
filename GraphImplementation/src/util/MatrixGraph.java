package util;

import java.util.ArrayList;

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

    public boolean addVertex(T content){
        if (content==null) {
           return false;
        }
        Vertex<T> newVertex= new Vertex<>(content);
        if(vertexList.add(newVertex)){
            return true;
        }
        return false;
    }

    public boolean addEdge(Vertex<T>v1, Vertex<T>v2){
        int indexV1= returnIndex(v1);
        int indexV2= returnIndex(v2);
        matrixAdj[indexV1][indexV2]= 1;
        matrixAdj[indexV2][indexV1]= 1;
        return true;
    }

    public int returnIndex(Vertex<T>v1){
        for(int i=0; i<vertexList.size(); i++){
            if(vertexList.get(i).getContent().equals(v1.getContent())){
                return i;
            }
        }
        return -1;
    }

    public Vertex<T> serchVertex(T content){
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


}
