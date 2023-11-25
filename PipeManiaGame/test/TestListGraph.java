import com.example.pipemaniagame.model.AdjacencyListGraph;
import com.example.pipemaniagame.model.Vertex;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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



        @Test
        public void testAddVertex() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            assertTrue(graph.addVertex(1));
        }

        @Test
        public void testAddNullVertex() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            assertFalse(graph.addVertex(null));
        }


        @Test
        public void testAddEdgeNonExistentVertices() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            Vertex<Integer> v1 = new Vertex<>(1);
            Vertex<Integer> v2 = new Vertex<>(2);
            assertFalse(graph.addEdge(v1, v2));
        }

        @Test
        public void testSearchVertex() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            graph.addVertex(1);
            Vertex<Integer> result = graph.searchVertex(1);
            assertNotNull(result);
        }

        @Test
        public void testSearchNonExistentVertex() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            Vertex<Integer> result = graph.searchVertex(7);
            assertNull(result);
        }

        @Test
        public void testSearchEmptyGraph() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            Vertex<Integer> result = graph.searchVertex(5);
            assertNull(result);
        }

        @Test
        public void testDeleteVertex() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            graph.addVertex(1);
            assertTrue(graph.deleteVertex(1));
            assertNull(graph.searchVertex(1));
        }

        @Test
        public void testDeleteNonExistentVertex() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            assertFalse(graph.deleteVertex(6));
        }

        @Test
        public void testDeleteNullVertex() {
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            assertFalse(graph.deleteVertex(null));
        }

        @Test
        public void testDijkstraEasierSolution() {
            setUp1();
            AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
            Vertex<Integer> source = new Vertex<>(1, new ArrayList<>());
            Vertex<Integer> drain = new Vertex<>(4, new ArrayList<>());
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);
            graph.addVertex(4);
            graph.addEdge(graph.searchVertex(1), graph.searchVertex(2));
            graph.addEdge(graph.searchVertex(1), graph.searchVertex(3));
            graph.addEdge(graph.searchVertex(2), graph.searchVertex(4));
            graph.addEdge(graph.searchVertex(3), graph.searchVertex(4));

            List<Vertex<Integer>> shortestPath = graph.easierSolutionDijkstra(source, drain);

            assertEquals(0, shortestPath.size());

        }


    @Test
    public void testDijkstraShortestPath() {
        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
        Vertex<Integer> source = new Vertex<>(1,  new ArrayList<>());
        Vertex<Integer> vertex2 = new Vertex<>(2, new ArrayList<>());
        Vertex<Integer> vertex3 = new Vertex<>(3, new ArrayList<>());
        Vertex<Integer> drain = new Vertex<>(4, new ArrayList<>());

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(source, vertex2);
        graph.addEdge(source, vertex3);
        graph.addEdge(vertex2, drain);
        graph.addEdge(vertex3, drain);

        List<Vertex<Integer>> shortestPath = graph.easierSolutionDijkstra(source, drain);

        assertEquals(3, shortestPath.size());
        assertEquals(source, shortestPath.get(0));

        assertEquals(drain, shortestPath.get(2));
    }

    @Test
    public void testDijkstraDisconnectedGraph() {
        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
        Vertex<Integer> source = new Vertex<>(1, new ArrayList<>());
        Vertex<Integer> drain = new Vertex<>(2, new ArrayList<>());

        graph.addVertex(1);
        graph.addVertex(2);



        List<Vertex<Integer>> shortestPath = graph.easierSolutionDijkstra(source, drain);

        assertTrue(shortestPath.isEmpty());
    }

    @Test
    public void testDijkstraUnreachableVertex() {
        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
        Vertex<Integer> source = new Vertex<>(1, new ArrayList<>());
        Vertex<Integer> vertex2 = new Vertex<>(2, new ArrayList<>());
        Vertex<Integer> vertex3 = new Vertex<>(3, new ArrayList<>());
        Vertex<Integer> drain = new Vertex<>(4, new ArrayList<>());

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);


        graph.addEdge(source, vertex2);
        graph.addEdge(vertex3, drain);

        List<Vertex<Integer>> shortestPath = graph.easierSolutionDijkstra(source, drain);

        assertTrue(shortestPath.isEmpty());
    }



}
