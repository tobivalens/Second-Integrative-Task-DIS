import com.example.pipemaniagame.model.AdjacencyListGraph;
import com.example.pipemaniagame.model.Vertex;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestListGraph {
    AdjacencyListGraph<Integer> g;

    public void setUp0(){

    }
    public void setUp1()
    {
       g = new AdjacencyListGraph<>();
    }

    public void setUp2(){
        setUp1();
        g.addVertex(1);
    }

    public void setUp3(){
        setUp1();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
    }

    @Test
    public void testAdd(){
        setUp1();
        assertTrue(g.addVertex(1));
    }
    @Test
    public void testAddNull(){
        setUp1();
        assertFalse(g.addVertex(null));
    }

    @Test
    public void TestSearchVertex(){
        setUp3();
        Vertex<Integer> response= g.searchVertex(2);
        assertNotNull(response);
    }

    @Test
    public void TestSearchValue(){
        setUp2();
        Vertex<Integer> response= g.searchVertex(1);
         boolean x= g.searchValue(response);
        assertTrue(x);
    }

    @Test
    public void TestSearchNonExistentVertex(){
        setUp3();
        Vertex<Integer> response= g.searchVertex(7);
        assertNull(response);
    }


    @Test
    public void testAddEdge(){
        setUp3();
        Vertex<Integer> vertex1 = g.searchVertex(1);
        Vertex<Integer> vertex2 = g.searchVertex(2);
        Vertex<Integer> vertex3 = g.searchVertex(3);

        assertTrue(g.addEdge(vertex1, vertex2));
        assertTrue( g.addEdge(vertex1, vertex2));
        assertTrue( g.addEdge(vertex2, vertex3));
    }

    @Test
    public void deleteVertexTest(){
        setUp3();
        assertTrue(g.deleteVertex(2));
        assertNull(g.searchVertex(2));
    }

    @Test
    public void deleteNonExistentVertexTest(){
        setUp1();
        assertFalse(g.deleteVertex(6));
    }

}
