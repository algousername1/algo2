package hw61;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphTest {

    @Test
    void testSimpleGraph() {
        int[][] matrix = {
                {0, 1, 0},
                {0, 0, 2},
                {3, 0, 0}
        };

        List<Edge> edges = Graph.transformMatrixToEdgeList(matrix);

        assertEquals(3, edges.size());
        assertTrue(edges.contains(new Edge(0, 1, 1)));
        assertTrue(edges.contains(new Edge(1, 2, 2)));
        assertTrue(edges.contains(new Edge(2, 0, 3)));
    }

    @Test
    void testEmptyGraph() {
        int[][] matrix = {
                {0, 0},
                {0, 0}
        };

        List<Edge> edges = Graph.transformMatrixToEdgeList(matrix);

        assertTrue(edges.isEmpty());
    }

    @Test
    void testSingleVertex() {
        int[][] matrix = {
                {0}
        };

        List<Edge> edges = Graph.transformMatrixToEdgeList(matrix);

        assertEquals(0, edges.size());
    }

    @Test
    void testSelfLoop() {
        int[][] matrix = {
                {5}
        };

        List<Edge> edges = Graph.transformMatrixToEdgeList(matrix);

        assertEquals(1, edges.size());
        assertEquals(new Edge(0, 0, 5), edges.get(0));
    }
}
