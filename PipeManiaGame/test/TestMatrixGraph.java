

import com.example.pipemaniagame.model.MatrixGraph;
import com.example.pipemaniagame.model.Vertex;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestMatrixGraph {

    MatrixGraph<Integer> mGraph;
    public void setUp1(){
        mGraph = new MatrixGraph<>(5);
    }
    public void setUp2(){
        setUp1();
        mGraph.addVertex(3);
        mGraph.addVertex(1);
        mGraph.addVertex(2);
        mGraph.addVertex(4);
        mGraph.addVertex(5);

    }
    public void setUp3(){
        setUp1();
        mGraph.addVertex(1);
        mGraph.addVertex(2);
        mGraph.addVertex(3);
    }

    public void setUp4(){
        setUp1();
        mGraph.addVertex(1);
        mGraph.addVertex(2);
        mGraph.addVertex(3);
        mGraph.addEdge(0, 1);
        mGraph.addEdge(0, 2);
        mGraph.addEdge(1, 0);
    }

    @Test
    public void testAdd(){
        setUp1();
        assertTrue(mGraph.addVertex(1));
    }
    @Test

    public void testAddNull(){
        setUp1();
        assertFalse(mGraph.addVertex(null));
    }
    @Test
    public void testAddWithoutInitialization(){
        assertThrows(NullPointerException.class, () -> {
            mGraph.addVertex(-1);
        });
    }


    @Test
    public void TestSearchVertex(){
        setUp3();
        Vertex<Integer> response= mGraph.serchVertex(2);
       assertNotNull(response);
    }

    @Test
    public void TestSearchNonExistentVertex(){
        setUp3();
        Vertex<Integer> response= mGraph.serchVertex(7);
        assertNull(response);
    }

    @Test
    public void testSearchEmptyGraph(){
        setUp1();
        Vertex<Integer> result = mGraph.serchVertex(5);
        assertNull(result);
    }

    @Test
    public void testIndexVertex(){
        setUp2();
        Vertex<Integer> result = mGraph.indexVertex(3);
        assertNotNull(result);
        assertEquals(4, result.getContent());
    }

    @Test
    public void testIndexVertexOutOfLimit(){
        setUp3();
            assertThrows(IndexOutOfBoundsException.class, () -> {
            mGraph.indexVertex(20);
        });
    }

    @Test
    public void testNotInitializedGraphIndexVertex(){
        assertThrows(NullPointerException.class, () -> {
              Vertex<Integer> result = mGraph.indexVertex(4);
        });
    }

    @Test
    public void tesGetIndexSuccessful(){
        setUp1();
        Vertex<Integer> newV= new Vertex<>(2);
        mGraph.addVertex(2);
        assertEquals(0, mGraph.returnIndex(newV));
    }

    @Test
    public void tesGetIndexNonExistent(){
        setUp3();
        Vertex<Integer> non= new Vertex<>(5) ;
        assertEquals(-1, mGraph.returnIndex(non));
    }

    @Test
    public void tesGetIndexEmptyGraph(){
        setUp1();
        Vertex<Integer> vertex = new Vertex<>(42);
        int result = mGraph.returnIndex(vertex);
        assertEquals(-1, result);
    }

    @Test
    public void testGetIndexNullVertex(){

        assertThrows(NullPointerException.class, () -> {
            Vertex<Integer> nullVertex = null;
            int result = mGraph.returnIndex(nullVertex);
            assertEquals(-1, result);
        });

    }


    @Test
    public void testAddEdge(){
        setUp3();
        assertTrue(mGraph.addEdge(0, 1));
        assertTrue( mGraph.addEdge(1, 0));
        assertTrue( mGraph.addEdge(2, 1));
    }

    @Test
    public void testAddEdgeOutOfBound(){
        setUp1();

           assertFalse(mGraph.addEdge(1, -5));

    }

    @Test
    public void testAddEdgeEmptyGraph(){
        setUp1();
        assertFalse(mGraph.addEdge(0, 1));
    }

    @Test
    public void deleteVertexTest(){
        setUp3();
        assertTrue(mGraph.deleteVertex(2));
        assertNull(mGraph.serchVertex(2));
    }

    @Test
    public void deleteNonExistentVertexTest(){
        setUp1();
        assertFalse(mGraph.deleteVertex(6));
    }

    @Test
    public void deleteNullVertex(){
        setUp1();
        assertFalse(mGraph.deleteVertex(null));
    }

    @Test
    public void testGetNeighbors() {
        setUp4();
        Vertex<Integer> vertex1 = mGraph.indexVertex(0);
        Vertex<Integer> vertex2 = mGraph.indexVertex(1);
        Vertex<Integer> vertex3 = mGraph.indexVertex(2);

        List<Vertex<Integer>> neighbors1 = mGraph.getNeighbors(vertex1);
        List<Vertex<Integer>> neighbors2 = mGraph.getNeighbors(vertex2);
        List<Vertex<Integer>> neighbors3 = mGraph.getNeighbors(vertex3);


        assertEquals(2, neighbors1.size());
        assertTrue(neighbors1.contains(vertex2));
        assertTrue(neighbors1.contains(vertex3));

        assertEquals(1, neighbors2.size());
        assertTrue(neighbors2.contains(vertex1));

        assertEquals(0, neighbors3.size());

    }

    @Test
    public void testDijkstraShortestPath() {
        MatrixGraph<Integer> graph = new MatrixGraph<>(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        Vertex<Integer> source = graph.indexVertex(0);
        Vertex<Integer> drain = graph.indexVertex(4);

        List<Vertex<Integer>> shortestPath = graph.dijkstra(source, drain);

        assertEquals(1, shortestPath.size());
        assertEquals(source, shortestPath.get(0));

    }

    @Test
    public void testDijkstraDisconnectedVertices() {
        MatrixGraph<Integer> graph = new MatrixGraph<>(3);
        Vertex<Integer> source = graph.indexVertex(0);
        Vertex<Integer> drain = graph.indexVertex(2);

        List<Vertex<Integer>> shortestPath = graph.dijkstra(source, drain);

        assertFalse(shortestPath.isEmpty());
    }

    @Test
    public void testDijkstraSameSourceAndDrain() {
        MatrixGraph<Integer> graph = new MatrixGraph<>(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        Vertex<Integer> sourceAndDrain = graph.indexVertex(0);

        List<Vertex<Integer>> shortestPath = graph.dijkstra(sourceAndDrain, sourceAndDrain);

        assertEquals(1, shortestPath.size());
        assertEquals(sourceAndDrain, shortestPath.get(0));
    }


}
