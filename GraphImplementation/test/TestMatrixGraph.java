

import org.junit.Test;
import util.MatrixGraph;
import util.Vertex;

import static org.junit.Assert.*;
public class TestMatrixGraph {

    MatrixGraph<Integer> mGraph;
    public void setUp1(){
        mGraph = new MatrixGraph<>(50);
    }
    public void setUp2(){
        setUp1();
        mGraph.addVertex(3);
    }
    public void setUp3(){
        setUp1();
        mGraph.addVertex(1);
        mGraph.addVertex(2);
        mGraph.addVertex(3);
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
    public void tesGetIndex(){
        setUp1();
        Vertex<Integer> newV= new Vertex<>(2);
        mGraph.addVertex(2);
        assertEquals(0, mGraph.returnIndex(newV));
    }

    @Test
    public void testAddEdge(){
        setUp3();
        Vertex<Integer> vertex1 = mGraph.serchVertex(1);
        Vertex<Integer> vertex2 = mGraph.serchVertex(2);
        Vertex<Integer> vertex3 = mGraph.serchVertex(3);

        assertTrue(mGraph.addEdge(vertex1, vertex2));
        assertTrue( mGraph.addEdge(vertex1, vertex2));
        assertTrue( mGraph.addEdge(vertex2, vertex3));
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


}
